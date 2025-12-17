package com.example.calculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.calculator.ALL_CLEAR
import com.example.calculator.EVALUATE
import com.example.calculator.ZERO
import com.example.calculator.cellsList
import com.example.calculator.specialSymbolsList
import com.example.calculator.symbolMap
import com.example.calculator.ui.theme.CalculatorTheme



@Composable
fun Calculator(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel = viewModel()
) {
    val state = viewModel.stateFlow.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        InputPanel(
            modifier = Modifier.weight(1f),
            state = state
        )

        SpecialSymbols(
            modifier = Modifier.padding(top = 16.dp)
        )

        KeyBoard(
            modifier = Modifier,
            onCellClick = viewModel::processCommand
        )
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
                                    CalculatorCommand.Input((symbolMap[cell] ?: Symbol.UNSUPPORTED))
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
