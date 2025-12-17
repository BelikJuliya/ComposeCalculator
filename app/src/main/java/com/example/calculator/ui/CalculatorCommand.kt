package com.example.calculator.ui

sealed interface CalculatorCommand {
    data object Clear : CalculatorCommand
    data object Evaluate : CalculatorCommand
    data class Input(val input: Symbol) : CalculatorCommand
}