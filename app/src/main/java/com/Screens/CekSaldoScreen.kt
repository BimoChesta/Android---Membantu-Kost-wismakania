package com.bimo0064.project.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bimo0064.project.data.DataStoreManager
import kotlinx.coroutines.launch

@Composable
fun CekSaldoScreen(dataStoreManager: DataStoreManager) {
    var saldo by remember { mutableIntStateOf(0) }
    var inputSaldo by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        saldo = dataStoreManager.loadBalance()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Uang Kas Wisma Kania",
                fontSize = 24.sp,
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Rp. $saldo",
                fontSize = 28.sp,
                color = Color(0xFF2B9E9E),
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Saldo Baru: $inputSaldo",
                fontSize = 20.sp,
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Keypad Input
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (row in 0..2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (col in 0..2) {
                            val number = 9 - (row * 3 + col)
                            NumberButton(number.toString()) {
                                inputSaldo += number.toString()
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(text = "Hapus") {
                    if (inputSaldo.isNotEmpty()) inputSaldo = inputSaldo.dropLast(1)
                }
                NumberButton("0") {
                    inputSaldo += "0"
                }
                ActionButton(text = "OK") {
                    val newSaldo = inputSaldo.toIntOrNull()
                    if (newSaldo != null && newSaldo >= 0) {
                        saldo = newSaldo
                        inputSaldo = ""
                        scope.launch {
                            dataStoreManager.saveBalance(saldo)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NumberButton(number: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(65.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B9E9E))
    ) {
        Text(
            text = number,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.height(56.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B9E9E))
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}