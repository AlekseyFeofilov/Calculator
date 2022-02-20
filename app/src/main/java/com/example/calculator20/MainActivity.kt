package com.example.calculator20

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.numberButtom.setGroupOnClickListener(input)
        binding.operattionButton.setGroupOnClickListener(operation)
        binding.buttonPercent.setOnClickListener(percent)
        binding.buttonAC.setOnClickListener {
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
            binding.buttonDivision.id -> calculator.setTypeofOperation(Operation.DIVISION)
            binding.buttonMultiplication.id -> calculator.setTypeofOperation(Operation.MULTIPLICATION)
            binding.buttonMinus.id -> calculator.setTypeofOperation(Operation.SUBTRACTION)
            binding.buttonPlus.id -> calculator.setTypeofOperation(Operation.ADDITION)
        }
    }

    private val input = View.OnClickListener { view ->
        if(calculator.inputLength() < 9 ||
            !calculator.inputIsInteger() && calculator.inputLength() == 9 ||
             calculator.inputIsNegative() && view.id == binding.buttonNegative.id) {
            when (view.id) {
                binding.buttonComma.id -> calculator.convertToFloat()
                binding.buttonNegative.id -> calculator.makeNegative()
                else -> calculator.addNumber(findViewById<Button>(view.id).text);
            }

            binding.answer.text = calculator.getInput()
        }
    }
}
