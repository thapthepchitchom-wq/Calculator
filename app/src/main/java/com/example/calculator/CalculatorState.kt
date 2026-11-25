package com.example.calculator

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculatorOperation? = null,
    val isResultDisplayed: Boolean = false
) {
    val displayText: String
        get() {
            if (number1.isEmpty()) return "0"
            val opSymbol = operation?.symbol ?: ""
            return if (opSymbol.isEmpty()) {
                number1
            } else {
                if (number2.isEmpty()) "$number1 $opSymbol" else "$number1 $opSymbol $number2"
            }
        }
}
