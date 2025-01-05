package com.example.lojaonline

import LoginUserScreen
import RegisterUserScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.lojaonline.ui.theme.LojaOnlneTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LojaOnlneTheme {
                LoginUserScreen()
                }
            }
        }
    }