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
import androidx.compose.ui.unit.sp
import com.bimo0064.project.data.DataStoreManager
import com.bimo0064.project.model.Pembayaran
import kotlinx.coroutines.launch

@Composable
fun KasScreen(dataStoreManager: DataStoreManager) {
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
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) { // Added a Column to organize elements vertically
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Riwayat Pembayaran",
                fontSize = 22.sp,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(payments) { pembayaran ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
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


@Composable
fun ActionButtonKas(text: String, onClick: () -> Unit) {
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