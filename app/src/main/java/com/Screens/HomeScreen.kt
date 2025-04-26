package com.bimo0064.project.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bimo0064.project.R

@Composable
fun HomeScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE0E0E0) // Light background color to match the image
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top section with "Cek Saldo" button
            Button(
                onClick = {
                    navController.navigate("cek_saldo")
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
            ) {
                Text(
                    text = "Cek Saldo",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                )
            }

            // Middle section with info buttons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate("informasi")
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(0.8f).height(56.dp)
                ) {
                    Text(text = "Informasi Kost", style = MaterialTheme.typography.bodyLarge)
                }

                Button(
                    onClick = {
                        navController.navigate("listrik")
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(0.8f).height(56.dp)
                ) {
                    Text(text = "Cek Pembayaran Listrik", style = MaterialTheme.typography.bodyLarge)
                }
            }

            // Bottom section with clickable QR code image
            Image(
                painter = painterResource(id = R.drawable.scan), // Replace with actual QR code resource
                contentDescription = "QR Code",
                modifier = Modifier
                    .size(180.dp)
                    .clickable {
                        navController.navigate("qr_detail")
                    }
            )
        }
    }
}