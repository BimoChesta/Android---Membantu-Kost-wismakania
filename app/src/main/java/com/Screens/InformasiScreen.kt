package com.bimo0064.project.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bimo0064.project.R

@Composable
fun InformasiScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6F0F2))
            .padding(16.dp)
    ) {
        // Gambar di atas
        Image(
            painter = painterResource(id = R.drawable.logobiasa),
            contentDescription = "Gambar Rumah Kost",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        // Baris dua kolom
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Kolom 1
            Column(
                modifier = Modifier.weight(1f)
            ) {
                CardInfo(title = "Alamat Kost", content = """
                    Wisma Kania, Gang Amdasari, RT.5/RW.1,
                    Kampung Managa Dua, Dayeuhkolot (Belakang mixue sukapura)
                    DAYEUHKOLOT, KAB.BANDUNG, JAWA BARAT, ID 40257
                """.trimIndent())

                Spacer(modifier = Modifier.height(16.dp))

                CardInfo(title = "Peraturan Kost", content = """
                    1. Setiap keluar masuk harap kunci kembali pagar dengan gembok pagar.
                    2. Tidak boleh membawa pasangan jika tidak mau mengerti tutornannya.
                    3. Setelah menggunakan dapur harap dibersihkan langsung.
                    4. Buang sampah pada tempatnya.
                """.trimIndent())
            }

            // Kolom 2
            Column(
                modifier = Modifier.weight(1f)
            ) {
                CardInfo(title = "Fasilitas Kost dan Kewajiban Penghuni", content = """
                    Fasilitas Kamar:
                    1. kasur
                    2. headboard kasur
                    3. bantal & guling
                    4. meja
                    5. lemari
                    6. kamar mandi dalam
                    7. ember & gayung

                    Fasilitas Umum:
                    1. dapur
                    2. alat masak
                    3. kulkas
                    4. rooftop
                    5. jemuran pakaian
                    6. ruang santai

                    Kewajiban Penghuni Kost:
                    1. membayar listrik setiap bulan, Bor/bulanan
                """.trimIndent())
            }
        }
    }
}

@Composable
fun CardInfo(title: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = title,
            color = Color(0xFF009999),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
            color = Color.DarkGray
        )
    }
}
