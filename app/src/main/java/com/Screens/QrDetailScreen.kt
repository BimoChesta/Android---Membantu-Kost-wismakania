package com.bimo0064.project.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        color = Color(0xFFE6F0F2)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // QR Code image with rounded corners
            Image(
                painter = painterResource(id = R.drawable.qr),
                contentDescription = "QR Code",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Form fields styled like InformasiScreen
            InfoCard(title = "Nama", content = nama, onValueChange = { nama = it })
            Spacer(modifier = Modifier.height(16.dp))
            InfoCard(title = "Kamar", content = kamar, onValueChange = { kamar = it })
            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox for "Sudah Bayar"
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

            // Save button
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

@Composable
fun InfoCard(title: String, content: String, onValueChange: (String) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            color = Color(0xFF2B9E9E),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = content,
            onValueChange = onValueChange,
            label = { Text(title) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }
}
