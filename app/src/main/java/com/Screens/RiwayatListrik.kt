package com.bimo0064.project.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bimo0064.project.data.DataStoreManager
import com.bimo0064.project.model.DayData

@Composable
fun RiwayatListrik() {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }

    var month by remember { mutableStateOf("April") }
    var year by remember { mutableStateOf(2025) }
    val dayDataMap = remember { mutableStateMapOf<String, DayData>() }

    LaunchedEffect(month, year) {
        dayDataMap.clear()
        dayDataMap.putAll(dataStoreManager.loadDayData(month, year.toString()))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFE6F0F2))
    ) {
        if (dayDataMap.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Riwayat Input:",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF2B9E9E)
                )
                Spacer(modifier = Modifier.height(8.dp))

                dayDataMap.entries.sortedBy { it.key.toInt() }.forEach { (day, data) ->
                    Card(
                        modifier = Modifier.padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.elevatedCardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Tanggal $day - ${data.name} (${if (data.isPaid) "Sudah Bayar" else "Belum Bayar"})",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
    ) {
        IconButton(
            onClick = {
                val newIndex = (currentIndex - 1 + 12) % 12
                val newMonth = months[newIndex]
                val newYear = if (newIndex == 11) year - 1 else year
                onMonthYearChange(newMonth, newYear)
            },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous Month",
                tint = Color.Black
            )
        }

        Text(
            text = "$month $year",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = {
                val newIndex = (currentIndex + 1) % 12
                val newMonth = months[newIndex]
                val newYear = if (newIndex == 0) year + 1 else year
                onMonthYearChange(newMonth, newYear)
            },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next Month",
                tint = Color.Black
            )
        }
    }
}