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
import com.example.stockwiz.view.login.main.MainPage

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: UserDatabaseHelper // Declare dbHelper here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dbHelper = UserDatabaseHelper(this) // Initialize dbHelper here

        setContent {
            StockWizTheme {
                val isRegistering = remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (isRegistering.value) {
                        RegistrationScreen(
                            dbHelper = dbHelper, // Pass dbHelper to RegistrationScreen
                            onNavigateToLogin = { isRegistering.value = false }
                        )
                    } else {
                        LoginScreen(
                            modifier = Modifier.padding(innerPadding),
                            dbHelper = dbHelper, // Pass dbHelper to LoginScreen
                            onNavigateToRegister = { isRegistering.value = true },
                            onLoginSuccess = {
                                // Nav-igate to the main application
                                setContent {
                                    StockWizTheme {
                                        MainPage()// Navigate to MainPage on successful login
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
