package com.bimo0064.project.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformasiScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Informasi Kost") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2B9E9E),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFE6F0F2)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.logobiasa),
                contentDescription = "Logo Kost",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            )

            Spacer(modifier = Modifier.height(24.dp))

            InfoCard(
                title = "Alamat Kost",
                content = """
                    Wisma Kania, Gang Amdasari, RT.5/RW.1,
                    Kampung Managa Dua, Dayeuhkolot (Belakang Mixue Sukapura)
                    DAYEUHKOLOT, KAB.BANDUNG, JAWA BARAT, ID 40257
                """.trimIndent()
            )

            Spacer(modifier = Modifier.height(16.dp))

            InfoCard(
                title = "Fasilitas & Kewajiban",
                content = """
                    Fasilitas Kamar:
                    - Kasur
                    - Headboard kasur
                    - Bantal & Guling
                    - Meja
                    - Lemari
                    - Kamar mandi dalam
                    - Ember & Gayung

                    Fasilitas Umum:
                    - Dapur
                    - Alat masak
                    - Kulkas
                    - Rooftop
                    - Jemuran pakaian
                    - Ruang santai

                    Kewajiban:
                    - Membayar listrik setiap bulan
                """.trimIndent()
            )
        }
    }
}

@Composable
fun InfoCard(title: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2B9E9E),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )
        )
    }
}
