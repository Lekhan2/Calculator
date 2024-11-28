package personal.project.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView?= null
    private var onlastnumeric: Boolean=true
    private var onlastDot: Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvInput = findViewById(R.id.tvInput)

    }
    fun onDigit(view : View){
        if ((view as Button).text=="clr"){
            tvInput?.text=""
        }
        else {
            tvInput?.append((view as Button).text)
            onlastnumeric=true
            onlastDot=false
        }
    }
    fun onDecimalPoint(view:View){
        if (onlastnumeric && !onlastDot){
            tvInput?.append(".")
            onlastnumeric=false
            onlastDot=true
        }
    }
    private fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("*")||value.contains("/")||value.contains("+")||value.contains("-")
        }
    }
    fun onOperator(view : View){
        tvInput?.text?.let {
            if (onlastnumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                onlastnumeric=false
                onlastDot=false
                }
        }
    }
    private fun removeZero(result: String):String{
        var value=result
        if(result.endsWith(".0")) {
            value= result.substring(0, -2)
        }
        return value
    }
    fun onequall(view: View){
        if (onlastnumeric){
            var tvnumber=tvInput?.text.toString()
            var prefix=""

            try {
                if(tvnumber.startsWith("-")){
                    prefix="-"
                    tvnumber=tvnumber.substring(1)
                }
                if(tvnumber.contains("-")){
                    var splitvalue=tvnumber.split("-")
                    var oneNumer=splitvalue[0]
                    var twoNumber=splitvalue[1]

                    if(prefix.isNotEmpty()){
                        oneNumer=prefix+oneNumer
                    }
                    tvInput?.text=(oneNumer.toDouble()-twoNumber.toDouble()).toString() 
                }else if(tvnumber.contains("+")){
                    var splitvalue=tvnumber.split("+")
                    var oneNumer=splitvalue[0]
                    var twoNumber=splitvalue[1]

                    if(prefix.isNotEmpty()){
                        oneNumer=prefix+oneNumer
                    }
                    tvInput?.text=removeZero((oneNumer.toLong()+twoNumber.toLong()).toString())
                }else if(tvnumber.contains("/")){
                    var splitvalue=tvnumber.split("/")
                    var oneNumer=splitvalue[0]
                    var twoNumber=splitvalue[1]

                    if(prefix.isNotEmpty()){
                        oneNumer=prefix+oneNumer
                    }
                    tvInput?.text=removeZero((oneNumer.toLong()/twoNumber.toLong()).toString())
                }else if(tvnumber.contains("*")){
                    var splitvalue=tvnumber.split("*")
                    var oneNumer=splitvalue[0]
                    var twoNumber=splitvalue[1]

                    if(prefix.isNotEmpty()){
                        oneNumer=prefix+oneNumer
                    }
                    tvInput?.text=removeZero((oneNumer.toLong()*twoNumber.toLong()).toString())
                }


            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }

    }

}