package com.bimo0064.project.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bimo0064.project.R
import com.bimo0064.project.data.DataStoreManager
import com.bimo0064.project.model.DayData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatListrik(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }

    var month by remember { mutableStateOf("April") }
    var year by remember { mutableStateOf(2025) }
    val dayDataMap = remember { mutableStateMapOf<String, DayData>() }

    LaunchedEffect(month, year) {
        dayDataMap.clear()
        dayDataMap.putAll(dataStoreManager.loadDayData(month, year.toString()))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Riwayat Listrik",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF197278)
                )
            )
        },
        containerColor = Color(0xFFF2FAFB)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Riwayat Penggunaan Listrik",
                style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF197278)),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (dayDataMap.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    dayDataMap.entries.sortedBy { it.key.toInt() }.forEach { (day, data) ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Tanggal $day",
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${data.name} - ${if (data.isPaid) "✅ Sudah Bayar" else "❌ Belum Bayar"}",
                                    fontSize = 14.sp,
                                    color = if (data.isPaid) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            } else {
                Text(
                    text = "Belum ada data untuk bulan ini.",
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    textAlign = TextAlign.Center
                )
            }

            ListrikMonthNavigation(
                month = month,
                year = year,
                onMonthYearChange = { newMonth, newYear ->
                    month = newMonth
                    year = newYear
                }
            )
        }
    }
}



@Composable
fun ListrikMonthNavigation(
    month: String,
    year: Int,
    onMonthYearChange: (String, Int) -> Unit
) {
    val months = remember {
        listOf(
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        )
    }

    val currentIndex = months.indexOf(month)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(2.dp, RoundedCornerShape(16.dp))
    ) {
        IconButton(onClick = {
            val newIndex = (currentIndex - 1 + 12) % 12
            val newMonth = months[newIndex]
            val newYear = if (newIndex == 11) year - 1 else year
            onMonthYearChange(newMonth, newYear)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_keyboard_double_arrow_left_24),
                contentDescription = "Bulan Sebelumnya",
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp)
            )
        }

        Text(
            text = "$month $year",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
            textAlign = TextAlign.Center
        )

        IconButton(onClick = {
            val newIndex = (currentIndex + 1) % 12
            val newMonth = months[newIndex]
            val newYear = if (newIndex == 0) year + 1 else year
            onMonthYearChange(newMonth, newYear)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_keyboard_double_arrow_right_24),
                contentDescription = "Bulan Berikutnya",
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
