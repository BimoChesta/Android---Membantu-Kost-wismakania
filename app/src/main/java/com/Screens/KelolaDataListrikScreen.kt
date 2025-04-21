package com.Screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore(name = "day_data_store")
private val DAY_DATA_KEY = stringPreferencesKey("day_data_json")

data class DayData(
    val name: String,
    val isPaid: Boolean
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KelolaDataPListrikScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var month by remember { mutableStateOf("Januari") }
    var yearText by remember { mutableStateOf("2025") }
    val dayDataMap = remember { mutableStateMapOf<String, DayData>() }

    var showDialog by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf<Int?>(null) }
    var inputName by remember { mutableStateOf("") }
    var isPaid by remember { mutableStateOf(false) }

    // Load DataStore on start
    LaunchedEffect(Unit) {
        val json = context.dataStore.data.first()[DAY_DATA_KEY]
        json?.let {
            val type = object : TypeToken<Map<String, DayData>>() {}.type
            val map: Map<String, DayData> = Gson().fromJson(it, type)
            dayDataMap.putAll(map)
        }
    }

    val daysInMonth = 31
    var dayCounter = 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
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

        Column {
            repeat(5) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(7) {
                        if (dayCounter <= daysInMonth) {
                            val key = dayCounter.toString()
                            DayBox(
                                day = dayCounter,
                                data = dayDataMap[key],
                                onClick = {
                                    selectedDay = dayCounter
                                    inputName = dayDataMap[key]?.name ?: ""
                                    isPaid = dayDataMap[key]?.isPaid ?: false
                                    showDialog = true
                                }
                            )
                            dayCounter++
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
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

                        // Save ke DataStore
                        val json = Gson().toJson(dayDataMap)
                        scope.launch {
                            context.dataStore.edit { prefs ->
                                prefs[DAY_DATA_KEY] = json
                            }
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
private fun DayBox(
    day: Int,
    data: DayData?,
    onClick: () -> Unit
) {
    val bgColor = when {
        data?.isPaid == true -> Color.Red
        data != null -> Color.Cyan
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(55.dp)
            .padding(4.dp)
            .background(color = bgColor, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = data?.name ?: day.toString(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
            if (data?.isPaid == true) {
                Icon(Icons.Default.Check, contentDescription = "Sudah Bayar", tint = Color.White)
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
