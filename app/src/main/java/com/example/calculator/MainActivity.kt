package com.example.calculator

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // ประกาศตัวแปร
    private lateinit var display: TextView
    private lateinit var txtExpression: TextView
    private var current = ""
    private var operator = ""
    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var result = 0.0
    private var isNewOperation = true

    // ลิสต์ปุ่มทั้งหมดแยกตามประเภท
    private lateinit var numberButtons: List<Button>
    private lateinit var actionButtons: List<Button>
    private lateinit var opButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // เชื่อม textview เข้ากับ layout
        display = findViewById(R.id.txtDisplay)
        txtExpression = findViewById(R.id.txtExpression)

        // จัดกลุ่มปุ่ม
        numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnDot, R.id.btnSign
        ).map { findViewById(it) }

        actionButtons = listOf(
            R.id.btnClear, R.id.btnDel
        ).map { findViewById(it) }

        opButtons = listOf(
            R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide, R.id.btnMod, R.id.btnEquals
        ).map { findViewById(it) }

        // กำหนด event ให้ปุ่มตัวเลข
        for (btn in numberButtons) {
            when (btn.id) {
                R.id.btnDot -> btn.setOnClickListener { addDot() }
                R.id.btnSign -> btn.setOnClickListener { toggleSign() }
                else -> btn.setOnClickListener { numberClicked(btn.text.toString()) }
            }
        }

        // เชื่อมโยงปุ่มคำนวณ + - * / %
        findViewById<Button>(R.id.btnPlus).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { operatorClicked("×") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { operatorClicked("÷") }
        findViewById<Button>(R.id.btnMod).setOnClickListener { operatorClicked("%") }

        // เชื่อมโยงปุ่มพิเศษ
        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnDel).setOnClickListener { deleteLast() }

        // ปุ่มเปลี่ยนธีม
        findViewById<Button>(R.id.btnThemeBlue).setOnClickListener { applyTheme("blue") }
        findViewById<Button>(R.id.btnThemePurple).setOnClickListener { applyTheme("purple") }
        findViewById<Button>(R.id.btnThemeGreen).setOnClickListener { applyTheme("green") }
        findViewById<Button>(R.id.btnThemeOrange).setOnClickListener { applyTheme("orange") }
        findViewById<Button>(R.id.btnThemeLight).setOnClickListener { applyTheme("light") }

        // โหลดธีมที่เคยเลือกไว้ (เริ่มต้นที่ Blue)
        val prefs = getSharedPreferences("calc_prefs", MODE_PRIVATE)
        val savedTheme = prefs.getString("selected_theme", "blue") ?: "blue"
        applyTheme(savedTheme)
    }

    private fun applyTheme(themeKey: String) {
        val prefs = getSharedPreferences("calc_prefs", MODE_PRIVATE)
        prefs.edit { putString("selected_theme", themeKey) }

        val (bgColor, displayTextColor, opBg, opText, actionBg, actionText, numBg, numText) = when (themeKey) {
            "purple" -> Octuple(
                R.color.theme_purple_bg, R.color.theme_purple_display_text,
                R.color.theme_purple_op_bg, R.color.theme_purple_op_text,
                R.color.theme_purple_action_bg, R.color.theme_purple_action_text,
                R.color.theme_purple_num_bg, R.color.theme_purple_num_text
            )
            "green" -> Octuple(
                R.color.theme_green_bg, R.color.theme_green_display_text,
                R.color.theme_green_op_bg, R.color.theme_green_op_text,
                R.color.theme_green_action_bg, R.color.theme_green_action_text,
                R.color.theme_green_num_bg, R.color.theme_green_num_text
            )
            "orange" -> Octuple(
                R.color.theme_orange_bg, R.color.theme_orange_display_text,
                R.color.theme_orange_op_bg, R.color.theme_orange_op_text,
                R.color.theme_orange_action_bg, R.color.theme_orange_action_text,
                R.color.theme_orange_num_bg, R.color.theme_orange_num_text
            )
            "light" -> Octuple(
                R.color.theme_light_bg, R.color.theme_light_display_text,
                R.color.theme_light_op_bg, R.color.theme_light_op_text,
                R.color.theme_light_action_bg, R.color.theme_light_action_text,
                R.color.theme_light_num_bg, R.color.theme_light_num_text
            )
            else -> Octuple( // "blue"
                R.color.theme_blue_bg, R.color.theme_blue_display_text,
                R.color.theme_blue_op_bg, R.color.theme_blue_op_text,
                R.color.theme_blue_action_bg, R.color.theme_blue_action_text,
                R.color.theme_blue_num_bg, R.color.theme_blue_num_text
            )
        }

        // เปลี่ยนสี Background
        findViewById<View>(R.id.main).setBackgroundColor(ContextCompat.getColor(this, bgColor))

        // เปลี่ยนสี Display Text
        display.setTextColor(ContextCompat.getColor(this, displayTextColor))
        txtExpression.setTextColor(ContextCompat.getColor(this, displayTextColor).and(0x99FFFFFF.toInt()))

        // เปลี่ยนสีปุ่มคำนวณ (+ - × ÷ =)
        val opBgColorState = ColorStateList.valueOf(ContextCompat.getColor(this, opBg))
        val opTextColorVal = ContextCompat.getColor(this, opText)
        for (btn in opButtons) {
            btn.backgroundTintList = opBgColorState
            btn.setTextColor(opTextColorVal)
        }

        // เปลี่ยนสีปุ่ม Action (AC DEL %)
        val actionBgColorState = ColorStateList.valueOf(ContextCompat.getColor(this, actionBg))
        val actionTextColorVal = ContextCompat.getColor(this, actionText)
        for (btn in actionButtons) {
            btn.backgroundTintList = actionBgColorState
            btn.setTextColor(actionTextColorVal)
        }

        // เปลี่ยนสีปุ่มตัวเลข (0-9 . +/-)
        val numBgColorState = ColorStateList.valueOf(ContextCompat.getColor(this, numBg))
        val numTextColorVal = ContextCompat.getColor(this, numText)
        for (btn in numberButtons) {
            btn.backgroundTintList = numBgColorState
            btn.setTextColor(numTextColorVal)
        }
    }

    private data class Octuple<A, B, C, D, E, F, G, H>(
        val a: A, val b: B, val c: C, val d: D,
        val e: E, val f: F, val g: G, val h: H
    )

    private fun numberClicked(value: String) {
        if (isNewOperation) {
            current = ""
            isNewOperation = false
        }
        if (current.length < 12) {
            current += value
            display.text = current
        }
    }

    private fun operatorClicked(op: String) {
        if (current.isNotEmpty()) {
            firstNumber = current.toDoubleOrNull() ?: 0.0
            operator = op
            txtExpression.text = "${formatNumber(firstNumber)} $op"
            isNewOperation = true
        }
    }

    private fun calculateResult() {
        if (current.isNotEmpty() && operator.isNotEmpty()) {
            secondNumber = current.toDoubleOrNull() ?: 0.0
            result = when (operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "×", "*" -> firstNumber * secondNumber
                "÷", "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                "%", "MOD" -> if (secondNumber != 0.0) firstNumber % secondNumber else firstNumber / 100.0
                else -> 0.0
            }

            txtExpression.text = "${formatNumber(firstNumber)} $operator ${formatNumber(secondNumber)} ="

            val resultString = if (result.isNaN()) {
                "Error"
            } else {
                formatNumber(result)
            }

            display.text = resultString
            current = if (resultString == "Error") "" else resultString
            isNewOperation = true
            operator = ""
        }
    }

    private fun clearAll() {
        current = ""
        operator = ""
        firstNumber = 0.0
        secondNumber = 0.0
        result = 0.0
        isNewOperation = true
        display.text = "0"
        txtExpression.text = ""
    }

    private fun addDot() {
        if (isNewOperation) {
            current = "0"
            isNewOperation = false
        }
        if (!current.contains(".")) {
            if (current.isEmpty()) current = "0"
            current += "."
            display.text = current
        }
    }

    private fun toggleSign() {
        if (current.isNotEmpty() && current != "0") {
            current = if (current.startsWith("-")) {
                current.substring(1)
            } else {
                "-$current"
            }
            display.text = current
        }
    }

    private fun deleteLast() {
        if (current.isNotEmpty() && !isNewOperation) {
            current = current.dropLast(1)
            display.text = current.ifEmpty { "0" }
        }
    }

    private fun formatNumber(value: Double): String {
        return if (value % 1 == 0.0 && value <= Long.MAX_VALUE.toDouble() && value >= Long.MIN_VALUE.toDouble()) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }
}

