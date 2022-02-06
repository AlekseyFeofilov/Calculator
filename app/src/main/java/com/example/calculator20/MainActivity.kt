package com.example.calculator20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.calculator20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var integer = true
        var text = ""
        /*var previous = 0f
        var current = 0f
        var currentString = ""
        var typeOfOperation = -1*/
        val input = View.OnClickListener { view ->
            if(text.length < 9 || text.length == 9 && !integer) {
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
                    binding.buttomComma.id -> {if(integer) text += ","; integer = false}
                    binding.buttomAC.id -> {text = ""; integer = true}
                    binding.buttomNegative.id -> {
                        text = if("-" in text) text.substring(1)
                        else "-$text"
                    }
                }

                binding.answer.text = text
            }
        }

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
        binding.buttomAC.setOnClickListener(input)
        binding.buttomNegative.setOnClickListener(input)

        /*binding.buttomPercent.setOnClickListener(input)
        binding.buttomDivision.setOnClickListener(input)
        binding.buttomMultiplication.setOnClickListener(input)
        binding.buttomMinus.setOnClickListener(input)
        binding.buttomPlus.setOnClickListener(input)
        binding.buttomEquals.setOnClickListener(input)*/
    }
}

/*
private operator fun CharSequence.plusAssign(s: String) {
    TODO("Not yet implemented")
}
*/

