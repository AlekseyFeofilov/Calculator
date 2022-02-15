package com.example.calculator20

import kotlin.math.abs
import kotlin.math.pow

open class TextData {
    var text: String = ""
    protected set
    protected val epsilon = 0.0000001f

    fun textCorrect() {
        if (text[0] == '.') {
            text = "0$text"
        } else if (text[0] == '-' && text[1] == '.') {
            text = "-0${text.substring(1)}"
        } else if (text[text.length - 1] == '.') {
            text = "${text}0"
        }
    }

    open fun formatOutput(value: Double): String{
        text = ""

        if(abs(value) > 1000000000000000000){
            return "ouerfl."
        }
        else if(value.toLong() / 100000000 in 1..9 || value.toLong() / 10000000 in -9..-1){
            return "${value.toLong()}"
        }
        else if(value > 100000000 || value < -10000000){
            val long = value.toLong()
            val digit = 10.0.pow(long.toString().length - 6)
            return (long / digit.toLong() * digit).toString()
        }
        else if (abs(value - value.toInt()) < epsilon &&
            value.toInt().toString().length <= 9 ) {
            return "${value.toInt()}"
        }
        else {
            val temp = "%.${10 - value.toInt().toString().length}f".format(value)
            var i = 9

            while(temp[i] == '0') i--

            return temp.substring(0, i + 1)
        }
    }

}