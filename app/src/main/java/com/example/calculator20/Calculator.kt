package com.example.calculator20

import kotlin.math.abs

class Calculator : TextData() {
    var typeOfOperation = 0
        set(value) {if (value in 0..4) field = value}
    private var current = 0.0

    fun formatOutput() :String {
        return formatOutput(current)
    }

    override fun formatOutput(value: Double): String {
        if (value > 1000000000000000000) {
            current = 0.0
        } else if (abs(value - value.toInt()) < epsilon &&
            value.toInt().toString().length < 9) {
            current = value.toInt() * 1.0
        }

        return super.formatOutput(value)
    }

    fun applyOperation(){
        when(typeOfOperation){
            0 -> current = text.toDouble()
            1 -> current /= text.toDouble()
            2 -> current *= text.toDouble()
            3 -> current -= text.toDouble()
            4 -> current += text.toDouble()
        }
    }

    fun usePercent() :Boolean =
        if(abs(current) < (epsilon) || text.isEmpty()) {
            reset()
            false
        }
        else {
            text = if (typeOfOperation < 3) "${text.toDouble() / 100}"
            else "${current / 100 * text.toDouble()}"
            true
        }


    fun convertToFloat(){
        if ("." !in text) text += "."
    }

    fun makeNegative(){
        text = if ("-" in text) text.substring(1) else "-$text"
    }

    fun addNumber(number: CharSequence){
        text += number
    }

    fun reset(){
        text = ""
        current = 0.0
        typeOfOperation = 0
    }
}