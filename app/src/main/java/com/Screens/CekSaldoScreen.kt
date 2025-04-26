package com.bimo0064.project.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bimo0064.project.data.DataStoreManager
import kotlinx.coroutines.launch

@Composable
fun CekSaldoScreen(dataStoreManager: DataStoreManager) {
    var saldo by remember { mutableStateOf(0) }
    var jumlahPembayaran by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        saldo = dataStoreManager.loadBalance()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFE6F0F2)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Saldo Uang Kas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Rp. $saldo",
            fontSize = 32.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = jumlahPembayaran,
            onValueChange = { jumlahPembayaran = it },
            label = { Text("Masukkan Jumlah Pembayaran") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val amount = jumlahPembayaran.toIntOrNull()
            if (amount != null && amount > 0 && amount <= saldo) {
                saldo -= amount
                jumlahPembayaran = ""
                scope.launch {
                    dataStoreManager.saveBalance(saldo)
                }
            } else {
            }
        }) {
            Text("Perbarui Saldo")
        }
    }
}
