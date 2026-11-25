package com.example.calculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.CalculatorAction
import com.example.calculator.CalculatorOperation
import com.example.calculator.CalculatorState

@Composable
fun CalculatorScreen(
    state: CalculatorState,
    onAction: (CalculatorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonSpacing = 12.dp

    val actionBg = Color(0xFF616161)
    val actionFg = Color.White
    val opBg = Color(0xFFFF9800)
    val opFg = Color.White
    val numBg = Color(0xFF2E2E38)
    val numFg = Color.White

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF121214))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            // Display Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = state.displayText,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Light,
                        fontSize = when {
                            state.displayText.length > 10 -> 40.sp
                            state.displayText.length > 7 -> 50.sp
                            else -> 64.sp
                        },
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Keypad Grid
            // Row 1: AC, DEL, %, ÷
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "AC",
                    backgroundColor = actionBg,
                    contentColor = actionFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Clear) }
                )
                CalculatorButton(
                    symbol = "DEL",
                    backgroundColor = actionBg,
                    contentColor = actionFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Delete) }
                )
                CalculatorButton(
                    symbol = "%",
                    backgroundColor = actionBg,
                    contentColor = actionFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Percent) }
                )
                CalculatorButton(
                    symbol = "÷",
                    backgroundColor = opBg,
                    contentColor = opFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Divide)) }
                )
            }

            // Row 2: 7, 8, 9, ×
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "7",
                    backgroundColor = numBg,
                    contentColor = numFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Number(7)) }
                )
                CalculatorButton(
                    symbol = "8",
                    backgroundColor = numBg,
                    contentColor = numFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Number(8)) }
                )
                CalculatorButton(
                    symbol = "9",
                    backgroundColor = numBg,
                    contentColor = numFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Number(9)) }
                )
                CalculatorButton(
                    symbol = "×",
                    backgroundColor = opBg,
                    contentColor = opFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Multiply)) }
                )
            }

            // Row 3: 4, 5, 6, -
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "4",
                    backgroundColor = numBg,
                    contentColor = numFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Number(4)) }
                )
                CalculatorButton(
                    symbol = "5",
                    backgroundColor = numBg,
                    contentColor = numFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Number(5)) }
                )
                CalculatorButton(
                    symbol = "6",
                    backgroundColor = numBg,
                    contentColor = numFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Number(6)) }
                )
                CalculatorButton(
                    symbol = "-",
                    backgroundColor = opBg,
                    contentColor = opFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Subtract)) }
                )
            }

            // Row 4: 1, 2, 3, +
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "1",
                    backgroundColor = numBg,
                    contentColor = numFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Number(1)) }
                )
                CalculatorButton(
                    symbol = "2",
                    backgroundColor = numBg,
                    contentColor = numFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Number(2)) }
                )
                CalculatorButton(
                    symbol = "3",
                    backgroundColor = numBg,
                    contentColor = numFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Number(3)) }
                )
                CalculatorButton(
                    symbol = "+",
                    backgroundColor = opBg,
                    contentColor = opFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Add)) }
                )
            }

            // Row 5: 0, ., =
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "0",
                    backgroundColor = numBg,
                    contentColor = numFg,
                    modifier = Modifier
                        .aspectRatio(2f)
                        .weight(2f),
                    onClick = { onAction(CalculatorAction.Number(0)) }
                )
                CalculatorButton(
                    symbol = ".",
                    backgroundColor = numBg,
                    contentColor = numFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Decimal) }
                )
                CalculatorButton(
                    symbol = "=",
                    backgroundColor = opBg,
                    contentColor = opFg,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f),
                    onClick = { onAction(CalculatorAction.Calculate) }
                )
            }
        }
    }
}
