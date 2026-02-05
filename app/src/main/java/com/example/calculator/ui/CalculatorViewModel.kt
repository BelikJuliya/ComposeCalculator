package com.example.calculator.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.mariuszgromada.math.mxparser.Expression
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
                evaluate()?.let {
                    _stateFlow.value = CalculatorState.Success(result = it)
                } ?: run {
                    _stateFlow.value = CalculatorState.Error(expression = expression)
                }
            }

            is CalculatorCommand.Input -> {
                val symbol = if (command.input == Symbol.PARENTHESIS) {
                    getCorrectParenthesis()
                } else command.input.value

                expression += symbol
                _stateFlow.value = CalculatorState.Input(
                    expression = expression,
                    result = evaluate() ?: ""
                )
            }
        }
    }

    private fun evaluate(): String? = expression
        .replace(',', '.')
        .replace('X', '*')
        .let { Expression(it) }
        .calculate()
        .takeIf { it.isFinite() }?.toString()

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
