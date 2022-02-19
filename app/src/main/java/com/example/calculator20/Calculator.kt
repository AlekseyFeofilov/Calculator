package com.example.calculator20

import kotlin.math.abs
import kotlin.math.pow

class Calculator {
    private var text: String = ""
    private var current = 0.0
    private var typeOfOperation = Operation.NOTHING
    private val epsilon = 0.0000001f

    fun inputIsEmpty() = Regex("\\d").find(text) != null

    fun inputLength() = text.length

    fun inputIsNegative() = "-" in text

    fun inputIsInteger() = "." !in text

    fun getInput() = text

    fun setTypeofOperation(value: Operation) {
        if (value in Operation.values()) typeOfOperation = value
    }

    fun applyOperation() {
        when (typeOfOperation) {
            Operation.NOTHING -> current = text.toDouble()
            Operation.DIVISION -> current /= text.toDouble()
            Operation.MULTIPLICATION -> current *= text.toDouble()
            Operation.SUBTRACTION -> current -= text.toDouble()
            Operation.ADDITION -> current += text.toDouble()
        }
    }

    fun usePercent(): Boolean =
        if (abs(current) < (epsilon) || text.isEmpty()) {
            reset()
            false
        }
        else {
            text =
                if (typeOfOperation == Operation.DIVISION || typeOfOperation == Operation.MULTIPLICATION)
                    "${text.toDouble() / 100}"
                else "${current / 100 * text.toDouble()}"
            true
        }

    fun convertToFloat() {
        if ("." !in text) text += "."
    }

    fun makeNegative() {
        text = if ("-" in text) text.substring(1) else "-$text"
    }

    fun addNumber(number: CharSequence) {
        text += number
    }

    fun reset() {
        text = ""
        current = 0.0
        typeOfOperation = Operation.NOTHING
    }

    fun textCorrect() {
        if (text[0] == '.') {
            text = "0$text"
        }
        else if (text[0] == '-' && text[1] == '.') {
            text = "-0${text.substring(1)}"
        }
        else if (text[text.length - 1] == '.') {
            text = "${text}0"
        }
    }

    fun formatOutput(): String {
        text = ""

        if (abs(current) > 1000000000000000000) {
            current = 0.0
            return "ouerfl."
        }
        else if (current.toLong() / 100000000 in 1..9 || current.toLong() / 10000000 in -9..-1) {
            return "${current.toLong()}"
        }
        else if (current > 100000000 || current < -10000000) {
            val long = current.toLong()
            val digit = 10.0.pow(long.toString().length - 6)
            return (long / digit.toLong() * digit).toString()
        }
        else if (abs(current - current.toInt()) < epsilon &&
            current.toInt().toString().length <= 9) {
            current = current.toInt() * 1.0
            return "${current.toInt()}"
        }
        else {
            val temp = "%.${10 - current.toInt().toString().length}f".format(current)
            var i = 9

            while (temp[i] == '0') i--

            return temp.substring(0, i + 1)
        }
    }
}