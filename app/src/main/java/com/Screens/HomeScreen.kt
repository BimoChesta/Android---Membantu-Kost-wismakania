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
fun HomeScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Selamat datang di Wisma Kania",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.logobiasa),
                contentDescription = "Logo Wisma Kania",
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    navController.navigate("informasi_kost") // Ganti dengan rute screen tujuan
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(0.6f)
                    .height(50.dp)
            ) {
                Text(text = "Jelajahi", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
