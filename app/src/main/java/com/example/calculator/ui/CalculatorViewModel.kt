package com.example.calculator.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class CalculatorViewModel : ViewModel() {

    private val _stateFlow: MutableStateFlow<CalculatorState> = MutableStateFlow(CalculatorState.Initial)
    val stateFlow = _stateFlow.asStateFlow()

    var expression: String = ""

    fun processCommand(command: CalculatorCommand) {
        when (command) {
            CalculatorCommand.Clear -> {
                expression = ""
                _stateFlow.value = CalculatorState.Initial
            }
            CalculatorCommand.Evaluate -> {
                val isError = Random.nextBoolean()
                if (isError) {
                    _stateFlow.value = CalculatorState.Error(expression = "100/0")
                } else {
                    _stateFlow.value = CalculatorState.Success(result = "100")
                }
            }

            is CalculatorCommand.Input -> {
                val symbol = if (command.input == Symbol.PARENTHESIS) {
                    getCorrectParenthesis()
                } else command.input.value

                expression += symbol
                _stateFlow.value = CalculatorState.Input(
                    expression = expression,
                    result = "100"
                )
            }
        }
    }

    private fun getCorrectParenthesis(): String {
        val openCount = expression.count { it == '(' }
        val closeCount = expression.count { it == ')' }
        return when {
            expression.isEmpty() -> "("
            expression.last().let {
                !it.isDigit() && it != '(' && it != 'π'
            } -> "("

            openCount > closeCount -> ")"
            else -> "("
        }
    }
}
