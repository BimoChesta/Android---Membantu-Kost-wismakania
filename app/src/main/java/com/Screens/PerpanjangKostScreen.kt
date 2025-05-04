package com.bimo0064.project.Screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import android.widget.Toast
import androidx.navigation.NavHostController
import com.bimo0064.project.model.DataPerpanjangKost

@Composable
fun PerpanjangKostScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf("1") }
    var selectedMonth by remember { mutableStateOf("Januari") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    var expandedRoom by remember { mutableStateOf(false) }
    var expandedMonth by remember { mutableStateOf(false) }

    val roomOptions = (1..12).map { it.toString() }
    val monthOptions = listOf(
        "Januari", "Februari", "Maret", "April", "Mei", "Juni",
        "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Perpanjang Kost", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown kamar
        Box {
            OutlinedTextField(
                value = selectedRoom,
                onValueChange = {},
                readOnly = true,
                label = { Text("Pilih Kamar") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expandedRoom = true }) {
                        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                    }
                }
            )

            DropdownMenu(
                expanded = expandedRoom,
                onDismissRequest = { expandedRoom = false }
            ) {
                roomOptions.forEach { room ->
                    DropdownMenuItem(
                        text = { Text("Kamar $room") },
                        onClick = {
                            selectedRoom = room
                            expandedRoom = false
                        }
                    )
                }
            }
        }

        // Dropdown bulan
        Box {
            OutlinedTextField(
                value = selectedMonth,
                onValueChange = {},
                readOnly = true,
                label = { Text("Pilih Bulan") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expandedMonth = true }) {
                        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                    }
                }
            )

            DropdownMenu(
                expanded = expandedMonth,
                onDismissRequest = { expandedMonth = false }
            ) {
                monthOptions.forEach { month ->
                    DropdownMenuItem(
                        text = { Text(month) },
                        onClick = {
                            selectedMonth = month
                            expandedMonth = false
                        }
                    )
                }
            }
        }

        // Tombol pilih gambar
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Pilih Gambar Bukti")
        }

        // Preview gambar
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
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
                Text("No image selected")
            }
        }

        Divider()

        Text("Data yang Diisi:", style = MaterialTheme.typography.titleMedium)
        Text("Nama: $name")
        Text("Kamar: $selectedRoom")
        Text("Bulan: $selectedMonth")
        Text("Gambar: ${imageUri?.lastPathSegment ?: "Belum dipilih"}")

        Button(onClick = {
            if (name.isNotEmpty() && imageUri != null) {
                val data = DataPerpanjangKost(
                    name,
                    selectedRoom,
                    selectedMonth,
                    imageUri?.toString()
                )

                // Simpan ke SharedPreferences
                saveData(context, data)

                Toast.makeText(context, "Data telah tercatat!", Toast.LENGTH_SHORT).show()
                navController.navigate("dataPerpanjangKost")
            } else {
                Toast.makeText(
                    context,
                    "Tolong isi semua data dan unggah gambar!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text("Submit")
        }
    }
}

private fun saveData(context: Context, data: DataPerpanjangKost) {
    val sharedPreferences = context.getSharedPreferences("KostData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("nama", data.nama)
    editor.putString("kamar", data.kamar)
    editor.putString("bulan", data.bulan)
    editor.putString("imageUri", data.imageUri)
    editor.apply()
}