package com.bimo0064.project.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bimo0064.project.data.DataStoreManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, navController: NavHostController, dataStoreManager: DataStoreManager) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope() // <-- Di sini tempat yang benar!

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE0E0E0)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Login", fontSize = 24.sp, color = Color.Black)

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (errorMessage.isNotBlank()) {
                Text(text = errorMessage, color = Color.Red)
            }

            Button(
                onClick = {
                    isLoading = true
                    coroutineScope.launch {
                        val user = dataStoreManager.loadUser()
                        if (user != null && user.username == username && user.password == password) {
                            onLoginSuccess()
                        } else {
                            errorMessage = "Maaf, username atau password salah!"
                        }
                        isLoading = false
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
                enabled = !isLoading
            ) {
                Text(if (isLoading) "Loading..." else "Login")
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(onClick = { navController.navigate("register") }) {
                Text(text = "Belum punya akun? Registrasi", color = Color.Blue)
            }
        }
    }
}
