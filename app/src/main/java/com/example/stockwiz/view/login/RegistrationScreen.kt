package com.example.stockwiz.view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockwiz.R
import com.example.stockwiz.UserDatabaseHelper

@Composable
fun RegistrationScreen(
    dbHelper: UserDatabaseHelper,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier // Add a modifier parameter
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), // Ensure padding is applied here
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Update with your logo
            contentDescription = "App Logo",
            modifier = Modifier.size(400.dp)
        )
        Text(text = "Register", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (password == confirmPassword) {
                if (dbHelper.insertUser(username, password)) {
                    onNavigateToLogin()
                } else {
                    errorMessage = "Registration failed. Try again."
                }
            } else {
                errorMessage = "Passwords do not match."
            }
        }) {
            Text("Register")
        }

        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Login")
        }
    }
}
