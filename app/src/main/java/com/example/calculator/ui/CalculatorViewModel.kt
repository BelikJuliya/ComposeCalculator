package com.example.calculator.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel: ViewModel() {

    private val _stateFlow = MutableStateFlow(
        Display(
            expression = "45x8",
            result = "364"
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

    val sb = StringBuilder()

    fun processCommand(input: CalculatorCommand) {
        val oldState = stateFlow.value
        when (input) {
            CalculatorCommand.Clear -> _stateFlow.value = Display(expression = "", result = "")
            CalculatorCommand.Evaluate -> TODO()
            is CalculatorCommand.Input -> {
//                sb.append()
//                state.value = Display(expression = oldState.expression.)
            }
        }
    }
}

data class Display(
    val expression: String,
    val result: String
)

sealed interface CalculatorCommand {
    data object Clear : CalculatorCommand
    data object Evaluate : CalculatorCommand
    data class Input(val input: Symbol) : CalculatorCommand
}

enum class Symbol {
    DIGIT_0,
    DIGIT_1,
    DIGIT_2,
    DIGIT_3,
    DIGIT_4,
    DIGIT_5,
    DIGIT_6,
    DIGIT_7,
    DIGIT_8,
    DIGIT_9,
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    PRECENT,
    POWER,
    FACTORIAL,
    SQRT,
    PI,
    DOT,
    PARENTHESIS,
    UNSUPPORTED
}
