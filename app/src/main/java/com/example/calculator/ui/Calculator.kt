package com.example.calculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.calculator.ui.theme.CalculatorTheme

const val TABLE_SIZE = 4
const val ALL_CLEAR = "AC"
const val ZERO = "0"

//private val cellsList =
//    listOf(
//        "AC", "(  )", "%", "÷",
//        "7", "8", "9", "X",
//        "4", "5", "6", "-",
//        "1", "2", "3", "+",
//        "0", ",", "="
//    )

private val cellsList = listOf(
    listOf("AC", "(  )", "%", "÷"),
    listOf("7", "8", "9", "X"),
    listOf("4", "5", "6", "-"),
    listOf("0", ",", "="),
)

private val specialSymbolsList = listOf(
    "√", "π", "^", "!"
)

@Composable
fun Calculator(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TopPanel(modifier = modifier.weight(3f))
        SpecialSymbols(
            modifier = modifier
                .weight(0.5f)
                .padding(top = 16.dp)
        )
        KeyBoard(modifier = modifier.weight(3f))
    }
}

@Composable
@Preview
fun CalculatorPreview() {
    CalculatorTheme {
        Calculator()
    }
}

@Composable
fun CellsLine(
    modifier: Modifier = Modifier,
    cellsList: List<String>
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        cellsList.forEach { cell ->
            Box(
                modifier = Modifier
                    .background(
                        color = defineBoxColor(cell)
                    )
                    .fillMaxWidth()
                    .weight(if (cell == ZERO) 2f else 1f)
                    .aspectRatio(if (cell == ZERO) 2 / 1f else 1f),
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
@Preview
fun KeyboardPreview() {
    CalculatorTheme {
        KeyBoard()
    }
}

@Composable
fun KeyBoard(modifier: Modifier = Modifier) {
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
                    cellsList = line
                )
            }
        }
    }
}

@Preview
@Composable
fun CellsLinePreview() {
//    CellsLine(cellsList = listOf("7", "8", "9", "X"))
    CalculatorTheme {
        CellsLine(cellsList = listOf("0", ",", "="))
    }
}

@Composable
fun defineBoxColor(text: String) =
    if (text == ALL_CLEAR) {
        MaterialTheme.colorScheme.onTertiaryContainer
    } else {
        if (text.isDigitsOnly()) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
    }

@Composable
fun defineWeight(text: String) =
    if (text == ZERO) 2f else 1f

@Composable
fun TopPanel(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 24.dp,
                    bottomEnd = 24.dp
                )
            ),
        contentAlignment = Alignment.BottomEnd
    ) {
        Expression()
    }
}

//@Composable
//@Preview
//fun TopPanelPreview() {
//    CalculatorTheme {
//        TopPanel()
//    }
//}

@Composable
fun Expression(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
            end = 44.dp,
            bottom = 14.dp
        ),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "45x8",
            fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Text(
            text = "360",
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

//@Composable
//@Preview
//fun ExpressionPreview() {
//    CalculatorTheme {
//        Expression()
//    }
//}

//@Composable
//@Preview
//fun SpecialSymbolsPreview() {
//    CalculatorTheme {
//        SpecialSymbols()
//    }
//}

@Composable
fun SpecialSymbols(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        specialSymbolsList.forEach {
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}
