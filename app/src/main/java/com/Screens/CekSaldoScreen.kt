package com.bimo0064.project.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bimo0064.project.data.DataStoreManager
import com.bimo0064.project.model.Pembayaran
import kotlinx.coroutines.launch

@Composable
fun CekSaldoScreen(dataStoreManager: DataStoreManager) {
    var saldo by remember { mutableIntStateOf(0) }
    var inputSaldo by remember { mutableStateOf("") }
    var payments by remember { mutableStateOf(listOf<Pembayaran>()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        saldo = dataStoreManager.loadBalance()
        payments = dataStoreManager.loadPayments()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE0E0E0)
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
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
            )

            Text(
                text = "Rp. $saldo",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = "Saldo Baru: $inputSaldo",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Column {
                for (row in 0..2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (col in 0..2) {
                            val number = 9 - (row * 3 + col)
                            Button(
                                onClick = { inputSaldo += number.toString() },
                                shape = RoundedCornerShape(100.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                                modifier = Modifier.size(60.dp)
                            ) {
                                Text(
                                    text = number.toString(),
                                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                                )
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
                Button(
                    onClick = { if (inputSaldo.isNotEmpty()) inputSaldo = inputSaldo.dropLast(1) },
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("Hapus", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
                }

                Button(
                    onClick = { inputSaldo += "0" },
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    modifier = Modifier.size(65.dp)
                ) {
                    Text("0", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
                }

                Button(
                    onClick = {
                        val newSaldo = inputSaldo.toIntOrNull()
                        if (newSaldo != null && newSaldo >= 0) {
                            saldo = newSaldo
                            inputSaldo = ""
                            scope.launch {
                                dataStoreManager.saveBalance(saldo)
                            }
                        }
                    },
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("OK", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Riwayat Pembayaran",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            LazyColumn {
                items(payments) { pembayaran ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Nama: ${pembayaran.nama}")
                            Text(text = "Kamar: ${pembayaran.kamar}")
                            Text(text = "Status: ${pembayaran.bukti}")
                        }
                    }
                }
            }
        }
    }
}
