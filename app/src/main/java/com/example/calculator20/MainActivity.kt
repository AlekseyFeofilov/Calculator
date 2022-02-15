package com.example.calculator20

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.example.calculator20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.numberButtom.setGroupOnClickListener(input)
        binding.operattionButton.setGroupOnClickListener(operation)
        binding.buttomPercent.setOnClickListener(percent)
        binding.buttomAC.setOnClickListener {
            calculator.reset()
            binding.answer.text = ""
        }
    }

    private fun Group.setGroupOnClickListener(listener: View.OnClickListener?){
        referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener(listener)
        }
    }

    private val percent = View.OnClickListener {
        if(calculator.usePercent()) operation
        else binding.answer.text = "0"
    }

    private val operation = View.OnClickListener { view->
        if (Regex("\\d").find(calculator.text) != null) {
            calculator.textCorrect()
            calculator.applyOperation();
            binding.answer.text = calculator.formatOutput()
        }

        when(view.id){
            binding.buttomDivision.id -> calculator.typeOfOperation = Operation.division.number
            binding.buttomMultiplication.id -> calculator.typeOfOperation = Operation.multiplication.number
            binding.buttomMinus.id -> calculator.typeOfOperation = Operation.subtraction.number
            binding.buttomPlus.id -> calculator.typeOfOperation = Operation.addition.number
        }
    }

    private val input = View.OnClickListener { view ->
        if(calculator.text.length < 9 ||
            "." in calculator.text && calculator.text.length == 9 ||
             "-" in calculator.text && view.id == binding.buttomNegative.id) {
            when (view.id) {
                binding.buttomComma.id -> calculator.convertToFloat()
                binding.buttomNegative.id -> calculator.makeNegative()
                else -> calculator.addNumber(findViewById<Button>(view.id).text);
            }

            binding.answer.text = calculator.text
        }
    }
}
