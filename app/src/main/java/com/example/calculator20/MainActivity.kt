package com.example.calculator20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.calculator20.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var text = ""
    var typeOfOperation = 0
    var current = 0.0
    val epsilon = 0.0000001f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttom0.setOnClickListener(input)
        binding.buttom1.setOnClickListener(input)
        binding.buttom2.setOnClickListener(input)
        binding.buttom3.setOnClickListener(input)
        binding.buttom4.setOnClickListener(input)
        binding.buttom5.setOnClickListener(input)
        binding.buttom6.setOnClickListener(input)
        binding.buttom7.setOnClickListener(input)
        binding.buttom8.setOnClickListener(input)
        binding.buttom9.setOnClickListener(input)
        binding.buttomComma.setOnClickListener(input)
        binding.buttomNegative.setOnClickListener(input)

        binding.buttomDivision.setOnClickListener(operation)
        binding.buttomMultiplication.setOnClickListener(operation)
        binding.buttomMinus.setOnClickListener(operation)
        binding.buttomPlus.setOnClickListener(operation)
        binding.buttomEquals.setOnClickListener(operation)

        binding.buttomPercent.setOnClickListener(percent)
        binding.buttomAC.setOnClickListener {
            text = ""; binding.answer.text = text;
            current = 0.0; typeOfOperation = 0
        }
    }

    private val percent = View.OnClickListener { view ->
        if(abs(current) < (epsilon) || text == "") {
            text = ""
            current = 0.0
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
            binding.buttomDivision.id -> typeOfOperation = 1
            binding.buttomMultiplication.id -> typeOfOperation = 2
            binding.buttomMinus.id -> typeOfOperation = 3
            binding.buttomPlus.id -> typeOfOperation = 4
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
        if(current.toLong() / 1000000000000000000 != 0L){
            binding.answer.text = getString(R.string.StackOverflow)
            current = 0.0
        }
        else if(current.toLong() / 100000000 in 1..9){
            binding.answer.text = "${current.toLong()}"
        }
        else if(current.toLong() / 100000000 != 0L){
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



