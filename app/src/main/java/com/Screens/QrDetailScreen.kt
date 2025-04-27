package com.bimo0064.project.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bimo0064.project.R
import com.bimo0064.project.data.DataStoreManager
import com.bimo0064.project.model.Pembayaran
import kotlinx.coroutines.launch

@Composable
fun QrDetailScreen(navController: NavHostController, dataStoreManager: DataStoreManager) {
    var nama by remember { mutableStateOf("") }
    var kamar by remember { mutableStateOf("") }
    var sudahBayar by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

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
            Image(
                painter = painterResource(id = R.drawable.qr),
                contentDescription = "QR Code",
                modifier = Modifier.size(300.dp)
            )

            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it },
                label = { Text("Nama") },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = kamar,
                onValueChange = { kamar = it },
                label = { Text("Kamar") },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp)
                    .toggleable(
                        value = sudahBayar,
                        onValueChange = { sudahBayar = it }
                    )
            ) {
                Checkbox(
                    checked = sudahBayar,
                    onCheckedChange = { sudahBayar = it }
                )
                Text(text = "Sudah Bayar", modifier = Modifier.padding(start = 8.dp))
            }

            Button(
                onClick = {
                    if (nama.isNotBlank() && kamar.isNotBlank()) {
                        val pembayaran = Pembayaran(
                            nama = nama,
                            kamar = kamar,
                            bukti = if (sudahBayar) "Sudah Bayar" else "Belum Bayar"
                        )
                        scope.launch {
                            dataStoreManager.savePayment(pembayaran)
                            nama = ""
                            kamar = ""
                            sudahBayar = false
                            navController.navigateUp() // Kembali ke screen sebelumnya
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(0.6f)
            ) {
                Text(text = "Simpan", color = Color.White)
            }
        }
    }
}
