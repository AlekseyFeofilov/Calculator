package com.example.calculator20

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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

    private val percent = View.OnClickListener {
        if(calculator.usePercent()) operation
        else binding.answer.text = "0"
    }

    private val operation = View.OnClickListener { view->
        if (calculator.inputIsEmpty()) {
            calculator.textCorrect()
            calculator.applyOperation();
            binding.answer.text = calculator.formatOutput()
        }

        when(view.id){
            binding.buttomDivision.id -> calculator.setTypeofOperation(Operation.DIVISION)
            binding.buttomMultiplication.id -> calculator.setTypeofOperation(Operation.MULTIPLICATION)
            binding.buttomMinus.id -> calculator.setTypeofOperation(Operation.SUBTRACTION)
            binding.buttomPlus.id -> calculator.setTypeofOperation(Operation.ADDITION)
        }
    }

    private val input = View.OnClickListener { view ->
        if(calculator.inputLength() < 9 ||
            !calculator.inputIsInteger() && calculator.inputLength() == 9 ||
             calculator.inputIsNegative() && view.id == binding.buttomNegative.id) {
            when (view.id) {
                binding.buttomComma.id -> calculator.convertToFloat()
                binding.buttomNegative.id -> calculator.makeNegative()
                else -> calculator.addNumber(findViewById<Button>(view.id).text);
            }

            binding.answer.text = calculator.getInput()
        }
    }
}
