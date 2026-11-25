package com.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorViewModel : ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Delete -> performDeletion()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Percent -> performPercent()
        }
    }

    private fun enterNumber(number: Int) {
        if (state.isResultDisplayed && state.operation == null) {
            // Start fresh if typing a number after result without operation
            state = CalculatorState(number1 = number.toString())
            return
        }

        if (state.operation == null) {
            if (state.number1.length >= MAX_NUM_LENGTH) return
            state = state.copy(
                number1 = state.number1 + number
            )
        } else {
            if (state.number2.length >= MAX_NUM_LENGTH) return
            state = state.copy(
                number2 = state.number2 + number
            )
        }
    }

    private fun enterDecimal() {
        if (state.isResultDisplayed && state.operation == null) {
            state = CalculatorState(number1 = "0.")
            return
        }

        if (state.operation == null) {
            if (!state.number1.contains(".")) {
                val newNum1 = if (state.number1.isEmpty()) "0." else state.number1 + "."
                state = state.copy(number1 = newNum1)
            }
        } else {
            if (!state.number2.contains(".")) {
                val newNum2 = if (state.number2.isEmpty()) "0." else state.number2 + "."
                state = state.copy(number2 = newNum2)
            }
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isEmpty() || state.number1 == "Error") {
            return
        }

        if (state.number2.isNotEmpty()) {
            performCalculation()
        }

        state = state.copy(
            operation = operation,
            isResultDisplayed = false
        )
    }

    private fun performDeletion() {
        if (state.isResultDisplayed && state.operation == null) {
            state = CalculatorState()
            return
        }

        when {
            state.number2.isNotEmpty() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )
            state.operation != null -> state = state.copy(
                operation = null
            )
            state.number1.isNotEmpty() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }

    private fun performPercent() {
        if (state.number2.isNotEmpty()) {
            val num2 = state.number2.toBigDecimalOrNull() ?: return
            val percentVal = num2.divide(BigDecimal(100), 8, RoundingMode.HALF_UP).stripTrailingZeros()
            state = state.copy(number2 = percentVal.toPlainString())
        } else if (state.number1.isNotEmpty() && state.operation == null && state.number1 != "Error") {
            val num1 = state.number1.toBigDecimalOrNull() ?: return
            val percentVal = num1.divide(BigDecimal(100), 8, RoundingMode.HALF_UP).stripTrailingZeros()
            state = state.copy(number1 = percentVal.toPlainString(), isResultDisplayed = true)
        }
    }

    private fun performCalculation() {
        val num1 = state.number1.toBigDecimalOrNull()
        val num2 = state.number2.toBigDecimalOrNull()
        val operation = state.operation

        if (num1 != null && num2 != null && operation != null) {
            val result = try {
                when (operation) {
                    is CalculatorOperation.Add -> num1.add(num2)
                    is CalculatorOperation.Subtract -> num1.subtract(num2)
                    is CalculatorOperation.Multiply -> num1.multiply(num2)
                    is CalculatorOperation.Divide -> {
                        if (num2.compareTo(BigDecimal.ZERO) == 0) {
                            null
                        } else {
                            num1.divide(num2, 10, RoundingMode.HALF_UP)
                        }
                    }
                }
            } catch (e: Exception) {
                null
            }

            if (result == null) {
                state = CalculatorState(number1 = "Error", isResultDisplayed = true)
            } else {
                val formattedResult = result.stripTrailingZeros().toPlainString()
                state = CalculatorState(
                    number1 = formattedResult,
                    isResultDisplayed = true
                )
            }
        }
    }

    companion object {
        private const val MAX_NUM_LENGTH = 12
    }
}
