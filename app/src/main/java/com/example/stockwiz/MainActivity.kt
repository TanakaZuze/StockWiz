package com.example.stockwiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.stockwiz.ui.theme.StockWizTheme
import com.example.stockwiz.view.login.LoginScreen
import com.example.stockwiz.view.login.RegistrationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockWizTheme {
                val isRegistering = remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (isRegistering.value) {
                        RegistrationScreen(
                            onNavigateToLogin = { isRegistering.value = false }
                        )
                    } else {
                        LoginScreen(
                            modifier = Modifier.padding(innerPadding),
                            onNavigateToRegister = { isRegistering.value = true }
                        )
                    }
                }
            }
        }
    }
}
