package com.bimo0064.project.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
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
import kotlinx.coroutines.launch

@Composable
fun KelolaDataListrikScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStoreManager = remember { DataStoreManager(context) }

    var month by remember { mutableStateOf("April") }
    var year by remember { mutableStateOf(2025) }
    val dayDataMap = remember { mutableStateMapOf<String, DayData>() }

    var showDialog by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf<Int?>(null) }
    var inputName by remember { mutableStateOf("") }
    var isPaid by remember { mutableStateOf(false) }

    LaunchedEffect(month, year) {
        dayDataMap.clear()
        dayDataMap.putAll(dataStoreManager.loadDayData(month, year.toString()))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFE6F0F2)) // Soft background color
    ) {
        // Calendar view
        CalendarContent(
            month = month,
            year = year.toString(),
            dayDataMap = dayDataMap,
            onDayClicked = { day ->
                selectedDay = day
                inputName = dayDataMap[day.toString()]?.name ?: ""
                isPaid = dayDataMap[day.toString()]?.isPaid ?: false
                showDialog = true
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Month Navigation
        MonthNavigation(
            month = month,
            year = year,
            onMonthYearChange = { newMonth, newYear ->
                month = newMonth
                year = newYear
            }
        )
    }

    if (showDialog && selectedDay != null) {
        InputNameDialog(
            selectedDay = selectedDay!!,
            inputName = inputName,
            isPaid = isPaid,
            onNameChanged = { inputName = it },
            onPaidToggle = { isPaid = !isPaid },
            onConfirm = {
                selectedDay?.let { day ->
                    if (inputName.isNotBlank()) {
                        val key = day.toString()
                        dayDataMap[key] = DayData(inputName, isPaid)

                        // Save changes
                        scope.launch {
                            dataStoreManager.saveDayData(month, year.toString(), dayDataMap)
                        }
                    }
                }
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun MonthNavigation(
    month: String,
    year: Int,
    onMonthYearChange: (String, Int) -> Unit
) {
    val months = listOf(
        "Januari", "Februari", "Maret", "April", "Mei", "Juni",
        "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    )

    val currentIndex = months.indexOf(month)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Tetapkan tinggi tetap agar tata letaknya stabil
            .padding(horizontal = 16.dp) // Ruang samping agar tidak terlalu mepet
    ) {
        // Tombol kiri (sebelumnya)
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

        // Teks bulan dan tahun di tengah
        Text(
            text = "$month $year",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )

        // Tombol kanan (berikutnya)
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



@Composable
private fun CalendarContent(
    month: String,
    year: String,
    dayDataMap: Map<String, DayData>,
    onDayClicked: (Int) -> Unit
) {
    val daysInMonth = getDaysInMonth(month, year)
    val columns = 5 // GANTI dari 5 ke 7
    val rows = (daysInMonth + columns - 1) / columns

    Column {
        (0 until rows).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                (0 until columns).forEach { column ->
                    val dayNumber = row * columns + column + 1
                    if (dayNumber <= daysInMonth) {
                        val key = dayNumber.toString()
                        DayBox(
                            day = dayNumber,
                            data = dayDataMap[key],
                            onClick = { onDayClicked(dayNumber) },
                            modifier = Modifier.weight(1f) // Tambah weight di sini
                        )
                    } else {
                        Spacer(
                            modifier = Modifier
                                .weight(1f) // Biar spasi kosong juga rata
                                .height(48.dp) // Sesuaikan tinggi spasi kosong
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun DayBox(
    day: Int,
    data: DayData?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = when {
        data?.isPaid == true -> Color(0xFF4CAF50)
        data != null -> Color.Cyan
        else -> Color.White
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .background(color = bgColor, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = data?.name.takeIf { !it.isNullOrEmpty() } ?: day.toString(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
            if (data?.isPaid == true) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Sudah Bayar",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


@Composable
private fun InputNameDialog(
    selectedDay: Int,
    inputName: String,
    isPaid: Boolean,
    onNameChanged: (String) -> Unit,
    onPaidToggle: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Data Tanggal $selectedDay") },
        text = {
            Column {
                OutlinedTextField(
                    value = inputName,
                    onValueChange = onNameChanged,
                    label = { Text("Nama") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isPaid,
                        onCheckedChange = { onPaidToggle() }
                    )
                    Text("Sudah Bayar?")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}

private fun getDaysInMonth(month: String, year: String): Int {
    val yearInt = year.toIntOrNull() ?: 2025
    return when (month) {
        "Januari", "Maret", "Mei", "Juli", "Agustus", "Oktober", "Desember" -> 31
        "April", "Juni", "September", "November" -> 30
        "Februari" -> if (isLeapYear(yearInt)) 29 else 28
        else -> 31
    }
}

private fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}
