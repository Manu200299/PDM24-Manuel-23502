package com.example.calculadora

import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.ui.theme.CalculadoraTheme
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme() {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Calculator(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

// Main calculator function
@Composable
fun Calculator(modifier: Modifier = Modifier) {
    // Variables
    var currentInput by remember { mutableStateOf("") }
    var previousInput by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf("") }
    var memory by remember { mutableStateOf(0.0) }

    Box() {
        // Main column that represents the whole calculator
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Display area
            Row(modifier = modifier.padding(16.dp)) {
                TextField(
                    value = if (result.isNotEmpty()) result else currentInput,
                    onValueChange = { currentInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontSize = 60.sp,
                        textAlign = TextAlign.End
                    )
                )
            }

            // Button click handling logic
            fun handleButtonClick(button: String) {
                when {
                    button in "0123456789." -> {
                        currentInput = handleNumberInput(button, currentInput)
                    }
                    button == "CE" -> {
                        currentInput = ""
                    }
                    button == "ON/C" -> {
                        currentInput = ""
                        previousInput = ""
                        operation = null
                        result = ""
                    }
                    button == "=" -> {
                        result = calculateResult(previousInput, currentInput, operation)
                        previousInput = ""
                        operation = null
                    }
                    button == "+" || button == "-" || button == "x" || button == "/" -> {
                        operation = button
                        previousInput = currentInput
                        currentInput = ""
                    }
                    button == "%" -> {
                        currentInput = handlePercentage(currentInput)
                    }
                    button == "+/-" -> {
                        currentInput = toggleSign(currentInput)
                    }
                    button == "√" -> {
                        currentInput = handleSquareroot(currentInput)
                    }
                    // Add more cases here if needed
                }
            }

            LazyVerticalGrid(
                modifier = Modifier.padding(16.dp),
                columns = GridCells.Fixed(4),
            ) {
                items(buttonList) { button ->
                    CalculatorButton(
                        button = button,
                        onClick = { handleButtonClick(button) }
                    )
                }
            }
        }
    }
}

// Buttons design
@Composable
fun CalculatorButton(button: String, onClick: () -> Unit) {
    Box(modifier = Modifier.padding(8.dp)) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            contentColor = Color.White,
            containerColor = getColor(button)
        ) {
            Text(
                text = button,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Colors the buttons specifically
fun getColor(button: String): Color {
    if (button == "CE" || button == "ON/C")
        return Color.Red
    if (button == "." || button == "=" || button == "0" || button == "1" || button == "2" || button == "3" || button == "4" || button == "5" || button == "6" || button == "7" || button == "8" || button == "9")
        return Color.Gray
    else
        return Color.Black
}

// List of buttons
val buttonList = listOf(
    "MC", "M-", "M+", "ON/C",
    "√", "%", "+/-", "CE",
    "7", "8", "9", "/",
    "4", "5", "6", "x",
    "1", "2", "3", "-",
    "0", ".", "=", "+"
)

// Handle number input
fun handleNumberInput(input: String, currentInput: String): String {
    return if (input == "." && currentInput.contains(".")) currentInput else currentInput + input
}

// Perform the calculation
fun calculateResult(previousInput: String, currentInput: String, operation: String?): String {
    val num1 = previousInput.toDoubleOrNull()
    val num2 = currentInput.toDoubleOrNull()

    if (num1 == null || num2 == null || operation == null) return "Erro"

    return when (operation) {
        "+" -> (num1 + num2).toString()
        "-" -> (num1 - num2).toString()
        "*" -> (num1 * num2).toString()
        "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Erro"
        else -> "Error"
    }
}

// Toggle the sign of the number
fun toggleSign(currentInput: String): String {
    val number = currentInput.toDoubleOrNull()
    return if (number != null) (-number).toString() else currentInput
}

// Handle percentage operation
fun handlePercentage(currentInput: String): String {
    val number = currentInput.toDoubleOrNull()
    return if (number != null) (number / 100).toString() else currentInput
}

// Handle squareroot
fun handleSquareroot(currentInput: String): String {
    val number = currentInput.toDoubleOrNull()
    return if (number != null && number >= 0) kotlin.math.sqrt(number).toString() else "Erro"
}



