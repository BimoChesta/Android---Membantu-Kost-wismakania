package com.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KelolaDataPListrikScreen() {
    var month by remember { mutableStateOf("Januari") }
    var yearText by remember { mutableStateOf("2025") }

    val daysInMonth = 31
    var dayCounter = 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header: Dropdown bulan dan input tahun
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropdownMenuBulan(
                selectedMonth = month,
                onMonthSelected = { month = it }
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = yearText,
                onValueChange = { yearText = it },
                label = { Text("Tahun") },
                modifier = Modifier.width(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kalender
        Column {
            for (week in 0 until 5) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (day in 0 until 7) {
                        if (dayCounter <= daysInMonth) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(4.dp)
                                    .background(
                                        color = if (dayCounter == 11) Color.Cyan else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (dayCounter == 11) "Vanisa" else dayCounter.toString(),
                                    textAlign = TextAlign.Center
                                )
                            }
                            dayCounter++
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuBulan(
    selectedMonth: String,
    onMonthSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val months = listOf(
        "Januari", "Februari", "Maret", "April", "Mei", "Juni",
        "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    )

    Box {
        OutlinedTextField(
            value = selectedMonth,
            onValueChange = {},
            modifier = Modifier
                .width(150.dp)
                .clickable { expanded = true },
            label = { Text("Bulan") },
            readOnly = true
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            months.forEach { month ->
                DropdownMenuItem(
                    onClick = {
                        onMonthSelected(month)
                        expanded = false
                    },
                    text = { Text(month) }
                )
            }
        }
    }
}
