package com.bimo0064.project.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun KeduaScreen(navController: NavHostController) {
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
            Text(
                text = "Aplikasi ini bertujuan untuk mempermudah pengelolaan data listrik dan pembayaran di Wisma Kania. " +
                        "Anda dapat mengelola data pembayaran, melihat riwayat, dan memastikan semua pembayaran tercatat dengan baik.",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("login") }) {
                Text("Masuk")
            }
        }
    }
}