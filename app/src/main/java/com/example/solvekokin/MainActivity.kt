package com.example.solvekokin

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var totalProblems: TextView
    private lateinit var correctCount: TextView
    private lateinit var incorrectCount: TextView
    private lateinit var percentage: TextView
    private lateinit var operand1: TextView
    private lateinit var operator: TextView
    private lateinit var operand2: TextView
    private lateinit var answer: EditText
    private lateinit var checkButton: Button
    private lateinit var startButton: Button

    private var correctAnswers = 0
    private var incorrectAnswers = 0
    private var totalAnswers = 0

    private var currentAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        totalProblems = findViewById(R.id.total_problems)
        correctCount = findViewById(R.id.correct)
        incorrectCount = findViewById(R.id.incorrect)
        percentage = findViewById(R.id.percentage)
        operand1 = findViewById(R.id.operand1)
        operator = findViewById(R.id.operator)
        operand2 = findViewById(R.id.operand2)
        answer = findViewById(R.id.answer)
        checkButton = findViewById(R.id.check_button)
        startButton = findViewById(R.id.start_button)

        startButton.setOnClickListener {
            generateProblem()
            startButton.isEnabled = false
            checkButton.isEnabled = true
            answer.isEnabled = true
        }

        checkButton.setOnClickListener {
            checkAnswer()
            startButton.isEnabled = true
            checkButton.isEnabled = false
            answer.isEnabled = false
        }
    }

    private fun generateProblem() {
        val num1 = Random.nextInt(10, 100)
        val num2 = Random.nextInt(10, 100)
        val operators = listOf("+", "-", "*", "/")
        val selectedOperator = operators.random()

        answer.text = null

        operand1.setBackgroundColor(Color.TRANSPARENT)
        operator.setBackgroundColor(Color.TRANSPARENT)
        operand2.setBackgroundColor(Color.TRANSPARENT)

        operand1.text = num1.toString()
        operand2.text = num2.toString()
        operator.text = selectedOperator

        currentAnswer = when (selectedOperator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> {
                val divisor = if (num1 % num2 == 0) num2 else Random.nextInt(1, 10)
                operand2.text = divisor.toString()
                num1 / divisor
            }
            else -> 0
        }
    }

    private fun checkAnswer() {
        val userAnswer = answer.text.toString().toIntOrNull()
        if (userAnswer == currentAnswer) {
            operand1.setBackgroundColor(Color.GREEN)
            operator.setBackgroundColor(Color.GREEN)
            operand2.setBackgroundColor(Color.GREEN)
            correctAnswers++
        } else {
            operand1.setBackgroundColor(Color.RED)
            operator.setBackgroundColor(Color.RED)
            operand2.setBackgroundColor(Color.RED)
            incorrectAnswers++
        }
        totalAnswers++

        updateStatistics()
    }

    private fun updateStatistics() {
        totalProblems.text = "Итого решено примеров: $totalAnswers"
        correctCount.text = "Правильно: $correctAnswers"
        incorrectCount.text = "Неправильно: $incorrectAnswers"
        val percent = if (totalAnswers > 0) (correctAnswers.toDouble() / totalAnswers * 100) else 0.0
        percentage.text = String.format("%.2f%%", percent)
    }
}
