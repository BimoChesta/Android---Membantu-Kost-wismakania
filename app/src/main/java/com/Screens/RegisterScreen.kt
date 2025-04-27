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
import com.bimo0064.project.model.User
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, dataStoreManager: DataStoreManager, navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Buat coroutine scope
    val coroutineScope = rememberCoroutineScope()

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
            Text(text = "Registrasi", fontSize = 24.sp, color = Color.Black)

            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nama") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())

            if (errorMessage.isNotBlank()) {
                Text(text = errorMessage, color = Color.Red)
            }

            Button(
                onClick = {
                    if (name.isNotBlank() && username.isNotBlank() && password.isNotBlank()) {
                        val user = User(name, username, password)
                        // Simpan ke DataStore
                        coroutineScope.launch {
                            dataStoreManager.saveUser(user)
                            // Setelah berhasil menyimpan, navigasi ke LoginScreen
                            onRegisterSuccess() // Panggil fungsi untuk navigasi
                            navController.navigate("login") // Navigasi ke LoginScreen
                        }
                    } else {
                        errorMessage = "Semua field harus diisi!"
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Daftar")
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(onClick = { navController.navigate("login") }) {
                Text(text = "Sudah punya akun? Login", color = Color.Blue)
            }
        }
    }
}