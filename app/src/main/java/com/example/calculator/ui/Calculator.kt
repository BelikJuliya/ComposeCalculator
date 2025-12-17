package com.example.calculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.ui.theme.CalculatorTheme

const val ALL_CLEAR = "AC"
const val EVALUATE = "="
const val ZERO = "0"

private val cellsList = listOf(
    listOf("AC", "(  )", "%", "÷"),
    listOf("7", "8", "9", "X"),
    listOf("4", "5", "6", "-"),
    listOf("1", "2", "3", "+"),
    listOf("0", ",", "="),
)

private val symbolMap = mapOf(
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

private val specialSymbolsList = listOf(
    "√", "π", "^", "!"
)

@Composable
fun Calculator(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel = viewModel()
) {
    val state = viewModel.stateFlow.collectAsState()

    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        InputPanel(modifier = modifier.weight(1f), state)
        SpecialSymbols(
            modifier = modifier
                .padding(top = 16.dp)
        )
        KeyBoard(modifier = modifier, onCellClick = {
            viewModel.processCommand(it)
        })
    }
}

@Composable
fun CellsLine(
    modifier: Modifier = Modifier,
    cellsList: List<String>,
    onCellClick: (CalculatorCommand) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        cellsList.forEach { cell ->
            Box(
                modifier = Modifier
                    .clip(
                        shape = CircleShape
                    )
                    .background(
                        color = defineBoxColor(cell)
                    )
                    .fillMaxWidth()
                    .weight(if (cell == ZERO) 2f else 1f)
                    .aspectRatio(if (cell == ZERO) 2 / 1f else 1f)
                    .clickable {
                        onCellClick(
                            when (cell) {
                                EVALUATE -> CalculatorCommand.Evaluate
                                ALL_CLEAR -> CalculatorCommand.Clear
                                else -> {
                                    CalculatorCommand.Input(symbolMap[cell] ?: Symbol.UNSUPPORTED)
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = cell,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 40.sp
                )
            }
        }
    }
}

@Composable
fun KeyBoard(modifier: Modifier = Modifier, onCellClick: (CalculatorCommand) -> Unit) {
    Box(
        modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(
                top = 23.dp,
                start = 23.dp,
                end = 30.dp,
                bottom = 20.dp
            )
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            cellsList.forEach { line ->
                CellsLine(
                    modifier = modifier,
                    cellsList = line,
                    onCellClick = onCellClick
                )
            }
        }
    }
}

@Composable
fun defineBoxColor(text: String) =
    if (text == ALL_CLEAR) {
        MaterialTheme.colorScheme.onTertiaryContainer
    } else {
        if (text.isDigitsOnly()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
    }

@Composable
fun InputPanel(modifier: Modifier = Modifier, state: State<CalculatorState>) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
            )
            .padding(
                end = 44.dp,
                bottom = 16.dp
            ),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {

        when (val currentState = state.value) {
            CalculatorState.Initial -> Unit

            is CalculatorState.Input -> {
                Text(
                    text = currentState.expression,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp,
                    lineHeight = 36.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    text = currentState.result,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    lineHeight = 17.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }

            is CalculatorState.Success -> {
                Text(
                    text = currentState.result,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp,
                    lineHeight = 36.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    text = "",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    lineHeight = 17.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }

            is CalculatorState.Error -> {
                Text(
                    text = currentState.expression,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp,
                    lineHeight = 36.sp,
                    color = MaterialTheme.colorScheme.error,
                )
                Text(
                    text = "",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    lineHeight = 17.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }

    }
}

@Composable
fun SpecialSymbols(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        specialSymbolsList.forEach { symbol ->
            Text(
                modifier = modifier.weight(1f),
                text = symbol,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

//@Composable
//@Preview
//fun ExpressionPreview() {
//    CalculatorTheme {
//        InputPanel()
//    }
//}

//@Composable
//@Preview
//fun SpecialSymbolsPreview() {
//    CalculatorTheme {
//        SpecialSymbols()
//    }
//}

//@Preview
//@Composable
//fun CellsLinePreview() {
////    CellsLine(cellsList = listOf("7", "8", "9", "X"))
//    CalculatorTheme {
//        CellsLine(cellsList = listOf("0", ",", "="), onCellClick = CalculatorCommand.Input(input = Symbol.DIGIT_4))
//    }
//}

//@Composable
//@Preview
//fun KeyboardPreview() {
//    CalculatorTheme {
//        KeyBoard()
//    }
//}

@Composable
@Preview
fun CalculatorPreview() {
    CalculatorTheme {
        Calculator()
    }
}
