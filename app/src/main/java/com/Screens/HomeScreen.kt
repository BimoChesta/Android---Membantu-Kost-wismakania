package com.bimo0064.project.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun HomeScreen(navController: NavHostController, dataStoreManager: DataStoreManager) {
    var saldo by remember { mutableStateOf(0) }
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
                text = "Saldo Uang Kas",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
            )
            Text(
                text = "Rp. $saldo",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Tombol untuk navigasi ke CekSaldoScreen
            Button(
                onClick = {
                    navController.navigate("cek_saldo")
                },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                modifier = Modifier.fillMaxWidth(0.8f).height(56.dp)
            ) {
                Text(text = "Cek Saldo", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
            }

            // Bagian tengah dengan tombol informasi
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate("informasi")
                    },
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    modifier = Modifier.fillMaxWidth(0.8f).height(56.dp)
                ) {
                    Text(text = "Informasi Kost", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
                }

                Button(
                    onClick = {
                        navController.navigate("listrik")
                    },
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    modifier = Modifier.fillMaxWidth(0.8f).height(56.dp)
                ) {
                    Text(text = "Cek Pembayaran Listrik", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
                }
            }

            // Gambar QR code yang dapat diklik
            Image(
                painter = painterResource(id = R.drawable.scan),
                contentDescription = "QR Code",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        navController.navigate("qr_detail")
                    }
            )
        }
    }
}