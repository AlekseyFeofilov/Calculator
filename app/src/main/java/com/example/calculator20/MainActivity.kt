package com.example.calculator20

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.example.calculator20.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var text = ""
    private var typeOfOperation = 0
    private var current = 0.0
    private val epsilon = 0.0000001f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.numberButtom.setGroupOnClickListener(input)
        binding.operattionButton.setGroupOnClickListener(operation)
        binding.buttomPercent.setOnClickListener(percent)
        binding.buttomAC.setOnClickListener {
            text = ""
            binding.answer.text = text
            current = 0.0
            typeOfOperation = 0
        }
    }

    private fun Group.setGroupOnClickListener(listener: View.OnClickListener?){
        referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener(listener)
        }
    }

    private val percent = View.OnClickListener {
        if(abs(current) < (epsilon) || text.isEmpty()) {
            text = ""
            current = 0.0
            typeOfOperation = 0
            binding.answer.text = "0"
            return@OnClickListener
        }

        text = if(typeOfOperation < 3) "${text.toDouble() / 100}"
        else "${current / 100 * text.toDouble()}"

        operation
    }

    private val operation = View.OnClickListener { view->
        if (Regex("\\d").find(text) != null) {
            textCorrect()

            if (typeOfOperation == 0) {
                current = text.toDouble()
            } else {
                when (typeOfOperation) {
                    1 -> current /= text.toDouble()
                    2 -> current *= text.toDouble()
                    3 -> current -= text.toDouble()
                    4 -> current += text.toDouble()
                }
            }

            text = ""
            output()
        }

        when(view.id){
            binding.buttomDivision.id -> typeOfOperation = Operation.division.number
            binding.buttomMultiplication.id -> typeOfOperation = Operation.multiplication.number
            binding.buttomMinus.id -> typeOfOperation = Operation.subtraction.number
            binding.buttomPlus.id -> typeOfOperation = Operation.addition.number
        }
    }

    private fun textCorrect() {
        if (text[0] == '.') {
            text = "0$text"
        } else if (text[0] == '-' && text[1] == '.') {
            text = "-0${text.substring(1)}"
        } else if (text[text.length - 1] == '.') {
            text = "${text}0"
        }
    }

    private fun output() {
        if(current > 1000000000000000000){
            binding.answer.text = getString(R.string.StackOverflow)
            current = 0.0
        }
        else if(current.toLong() / 100000000 in 1..9){
            binding.answer.text = "${current.toLong()}"
        }
        else if(current > 100000000){
            val long = current.toLong()
            val digit = 10.0.pow(long.toString().length - 6)
            binding.answer.text = (long / digit.toLong() * digit).toString()
        }
        else if (abs(current - current.toInt()) < epsilon &&
            current.toInt().toString().length < 9 ) {
            binding.answer.text = "${current.toInt()}"
            current = current.toInt() * 1.0
        }
        else {
            val integer = current.toInt()
            val temp = "%.${10 - integer.toString().length}f".format(current)
            var i = 9

            while(temp[i] == '0') i--

            binding.answer.text = temp.substring(0, i + 1)
        }
    }

    private val input = View.OnClickListener { view ->
        if(text.length < 9 ||
            "." in text && text.length == 9) {
            when (view.id) {
                binding.buttom0.id -> text += "0"
                binding.buttom1.id -> text += "1"
                binding.buttom2.id -> text += "2"
                binding.buttom3.id -> text += "3"
                binding.buttom4.id -> text += "4"
                binding.buttom5.id -> text += "5"
                binding.buttom6.id -> text += "6"
                binding.buttom7.id -> text += "7"
                binding.buttom8.id -> text += "8"
                binding.buttom9.id -> text += "9"
                binding.buttomComma.id -> { if ("." !in text) text += "." }
                binding.buttomNegative.id -> { text = if("-" in text) text.substring(1) else "-$text" }
            }
            binding.answer.text = text
        }
    }
}
