package com.bimo0064.project.Screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.bimo0064.project.model.DataPerpanjangKost
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun DataPerpanjangKostScreen(navController: NavHostController) {
    val context = LocalContext.current
    var data by remember { mutableStateOf(loadData(context)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Data Perpanjangan Kost", style = MaterialTheme.typography.headlineSmall)

        if (data != null) {
            Text("Nama: ${data!!.nama}")
            Text("Kamar: ${data!!.kamar}")
            Text("Bulan: ${data!!.bulan}")

            Spacer(modifier = Modifier.height(8.dp))

            if (!data!!.imageUri.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(Uri.parse(data!!.imageUri)),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tidak ada gambar")
                }
            }
        } else {
            Text("Tidak ada data yang dikirim.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Kembali")
        }
    }
}

private fun loadData(context: Context): DataPerpanjangKost? {
    val sharedPreferences = context.getSharedPreferences("KostData", Context.MODE_PRIVATE)
    val nama = sharedPreferences.getString("nama", null)
    val kamar = sharedPreferences.getString("kamar", null)
    val bulan = sharedPreferences.getString("bulan", null)
    val imageUri = sharedPreferences.getString("imageUri", null)

    return if (nama != null && kamar != null && bulan != null && imageUri != null) {
        DataPerpanjangKost(nama, kamar, bulan, imageUri)
    } else {
        null
    }
}