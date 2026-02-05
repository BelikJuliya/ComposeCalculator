package com.example.calculator

import com.example.calculator.ui.Symbol

val symbolMap = mapOf(
    "0" to Symbol.DIGIT_0,
    "1" to Symbol.DIGIT_1,
    "2" to Symbol.DIGIT_2,
    "3" to Symbol.DIGIT_3,
    "4" to Symbol.DIGIT_4,
    "5" to Symbol.DIGIT_5,
    "6" to Symbol.DIGIT_6,
    "7" to Symbol.DIGIT_7,
    "8" to Symbol.DIGIT_8,
    "9" to Symbol.DIGIT_9,

    "+" to Symbol.ADD,
    "-" to Symbol.SUBTRACT,
    "X" to Symbol.MULTIPLY,
    "÷" to Symbol.DIVIDE,
    "%" to Symbol.PRECENT,
    "^" to Symbol.POWER,
    "!" to Symbol.FACTORIAL,
    "√" to Symbol.SQRT,
    "^" to Symbol.PI,
    "," to Symbol.DOT,
    "(  )" to Symbol.PARENTHESIS
)

val specialSymbolsList = listOf(
    "√", "π", "^", "!"
)

val cellsList = listOf(
    listOf("AC", "(  )", "%", "÷"),
    listOf("7", "8", "9", "X"),
    listOf("4", "5", "6", "-"),
    listOf("1", "2", "3", "+"),
    listOf("0", ",", "="),
)

const val ALL_CLEAR = "AC"
const val EVALUATE = "="
const val ZERO = "0"