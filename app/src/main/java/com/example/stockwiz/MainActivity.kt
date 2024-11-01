package com.example.stockwiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.stockwiz.ui.theme.StockWizTheme
import com.example.stockwiz.view.login.LoginScreen
import com.example.stockwiz.view.login.RegistrationScreen
import com.example.stockwiz.view.main.MainPage

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: UserDatabaseHelper
    private lateinit var inventoryDbHelper: InventoryDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = UserDatabaseHelper(this)
        inventoryDbHelper = InventoryDatabaseHelper(this)

        setContent {
            StockWizTheme {
                var isLoggedIn by remember { mutableStateOf(false) }
                var isRegistering by remember { mutableStateOf(false) }

                Scaffold { paddingValues ->
                    when {
                        isRegistering -> RegistrationScreen(
                            dbHelper = dbHelper,
                            onNavigateToLogin = { isRegistering = false },
                            modifier = Modifier.padding(paddingValues)
                        )
                        isLoggedIn -> MainPage(
                            inventoryDbHelper = inventoryDbHelper,
                            modifier = Modifier.padding(paddingValues)
                        )
                        else -> LoginScreen(
                            dbHelper = dbHelper,
                            onNavigateToRegister = { isRegistering = true },
                            onLoginSuccess = { isLoggedIn = true },
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}
