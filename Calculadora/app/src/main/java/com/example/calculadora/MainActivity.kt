package com.example.calculadora

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                        onClick = {

                        }
                    )
                }
            }
        }
    }
}


@Composable
fun Calculator(onClick:() -> Unit,modifier: Modifier = Modifier){

    Column {

        //    Primeira row = painel digital (input e output)
        Row{
            TextField(value = "", label = { Text("Numeros...")}, onValueChange = {})
        }

        //  Todos os botoes
        Row{
            Column {
                Button(onClick = onClick) {
                    Text("MC")
                }
                Button(onClick = onClick) {
                    Text("</")
                }
                Button(onClick = onClick) {
                    Text("7")
                }
                Button(onClick = onClick) {
                    Text("4")
                }
                Button(onClick = onClick) {
                    Text("1")
                }
                Button(onClick = onClick) {
                    Text("0")
                }
            }
            Column {
                Button(onClick = onClick) {
                    Text("M-")
                }
                Button(onClick = onClick) {
                    Text("%")
                }
                Button(onClick = onClick) {
                    Text("8")
                }
                Button(onClick = onClick) {
                    Text("5")
                }
                Button(onClick = onClick) {
                    Text("2")
                }
                Button(onClick = onClick) {
                    Text(".")
                }
            }
            Column {
                Button(onClick = onClick) {
                    Text("M+")
                }
                Button(onClick = onClick) {
                    Text("+/-")
                }
                Button(onClick = onClick) {
                    Text("9")
                }
                Button(onClick = onClick) {
                    Text("6")
                }
                Button(onClick = onClick) {
                    Text("3")
                }
                Button(onClick = onClick) {
                    Text("=")
                }
            }
            Column {
                Button(onClick = onClick) {
                    Text("ON/C")
                }
                Button(onClick = onClick) {
                    Text("CE")
                }
                Button(onClick = onClick) {
                    Text("/")
                }
                Button(onClick = onClick) {
                    Text("x")
                }
                Button(onClick = onClick) {


                    Text("-")
                }
                Button(onClick = onClick) {
                    Text("+")
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraTheme() {
        Calculator(onClick = {})
    }
}