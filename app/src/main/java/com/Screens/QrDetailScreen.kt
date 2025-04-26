package com.bimo0064.project.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bimo0064.project.R

@Composable
fun QrDetailScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE0E0E0) // Light background color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top section with balance information
            Text(
                text = "Saldo uang kas",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            )
            Text(
                text = "Rp. 17.000",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            // Middle section for QR code
            Image(
                painter = painterResource(id = R.drawable.scan), // Replace with actual QR code resource
                contentDescription = "QR Code",
                modifier = Modifier.size(180.dp)
            )

            Text(
                text = "Masukkan data pembayaran",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Back button
            Button(
                onClick = {
                    navController.popBackStack() // Navigate back to previous screen
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
                    .padding(top = 16.dp)
            ) {
                Text(text = "Kembali", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}