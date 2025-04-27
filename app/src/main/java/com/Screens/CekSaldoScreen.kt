package com.bimo0064.project.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bimo0064.project.data.DataStoreManager
import kotlinx.coroutines.launch

@Composable
fun CekSaldoScreen(dataStoreManager: DataStoreManager) {
    var saldo by remember { mutableStateOf(0) }
    var inputSaldo by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Memuat saldo dari DataStore saat komponen pertama kali dimuat
    LaunchedEffect(Unit) {
        saldo = dataStoreManager.loadBalance()
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
            verticalArrangement = Arrangement.SpaceBetween
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

            // Menampilkan input saldo
            Text(
                text = "Saldo Baru: $inputSaldo",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Tombol untuk angka 1-9
            Column {
                for (row in 0..2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (col in 1..3) {
                            val number = row * 3 + col
                            if (number <= 9) {
                                Button(
                                    onClick = { inputSaldo += number.toString() },
                                    shape = RoundedCornerShape(100.dp), // Kotak
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                                    modifier = Modifier.size(60.dp)
                                ) {
                                    Text(text = number.toString(), style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
                                }
                            }
                        }
                    }
                }
            }

            // Jarak antara tombol angka dan tombol Delete/OK
            Spacer(modifier = Modifier.height(16.dp))

            // Tombol untuk menghapus dan mengonfirmasi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { if (inputSaldo.isNotEmpty()) inputSaldo = inputSaldo.dropLast(1) }, // Menghapus angka terakhir
                    shape = RoundedCornerShape(100.dp), // Kotak
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    modifier = Modifier
                        .weight(1f) // Memperluas tombol untuk memanjang
                        .height(56.dp) // Tinggi tombol
                        .padding(4.dp) // Jarak antar tombol
                ) {
                    Text(text = "Hapus", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
                }

                Button(
                    onClick = {
                        val newSaldo = inputSaldo.toIntOrNull()
                        if (newSaldo != null && newSaldo >= 0) {
                            saldo = newSaldo // Update saldo
                            inputSaldo = "" // Reset input
                            scope.launch {
                                dataStoreManager.saveBalance(saldo) // Simpan saldo ke DataStore
                            }
                        }
                    },
                    shape = RoundedCornerShape(0.dp), // Kotak
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    modifier = Modifier
                        .weight(1f) // Memperluas tombol untuk memanjang
                        .height(56.dp) // Tinggi tombol
                        .padding(4.dp) // Jarak antar tombol
                ) {
                    Text(text = "OK", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
                }
            }
        }
    }
}