package com.example.calculator.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class CalculatorViewModel : ViewModel() {

    private val _stateFlow: MutableStateFlow<CalculatorState> = MutableStateFlow(CalculatorState.Initial)
    val stateFlow = _stateFlow.asStateFlow()

    fun processCommand(input: CalculatorCommand) {
        when (input) {
            CalculatorCommand.Clear -> _stateFlow.value = CalculatorState.Initial
            CalculatorCommand.Evaluate -> {
                val isError = Random.nextBoolean()
                if (isError) {
                    _stateFlow.value = CalculatorState.Error(expression = "100/0")
                } else {
                    _stateFlow.value = CalculatorState.Success(result = "100")
                }
            }

            is CalculatorCommand.Input -> {
                _stateFlow.value = CalculatorState.Input(
                    expression = input.input.name,
                    result = "100"
                )
            }
        }
    }
}
