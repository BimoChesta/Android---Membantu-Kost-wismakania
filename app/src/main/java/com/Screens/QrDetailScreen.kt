package com.bimo0064.project.Screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.bimo0064.project.data.DataStoreManager
import com.bimo0064.project.model.Pembayaran
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrDetailScreen(navController: NavHostController, dataStoreManager: DataStoreManager) {
    var nama by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf("1") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var sudahBayar by remember { mutableStateOf(false) }
    var expandedRoom by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    val roomOptions = (1..7).map { it.toString() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Pembayaran") },
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Kartu Form
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = nama,
                        onValueChange = { nama = it },
                        label = { Text("Nama") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Dropdown kamar
                    Box {
                        OutlinedTextField(
                            value = "Kamar $selectedRoom",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Pilih Kamar") },
                            trailingIcon = {
                                IconButton(onClick = { expandedRoom = true }) {
                                    Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
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

                    // Checkbox Sudah Bayar
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = sudahBayar,
                            onCheckedChange = { sudahBayar = it }
                        )
                        Text("Sudah Bayar", modifier = Modifier.padding(start = 8.dp))
                    }

                    // Tombol pilih gambar
                    Button(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B9E9E))
                    ) {
                        Text("Upload Bukti", color = Color.White)
                    }

                    // Preview gambar
                    if (imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Belum ada gambar dipilih", color = Color.DarkGray)
                        }
                    }
                }
            }

            // Tombol Simpan
            Button(
                onClick = {
                    if (nama.isNotBlank() && imageUri != null) {
                        val pembayaran = Pembayaran(
                            nama = nama,
                            kamar = selectedRoom,
                            bukti = if (sudahBayar) "Sudah Bayar" else "Belum Bayar",
                            imageUri = imageUri.toString()
                        )
                        scope.launch {
                            dataStoreManager.savePayment(pembayaran)
                            Toast.makeText(context, "Data tersimpan!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    } else {
                        Toast.makeText(context, "Isi nama dan upload gambar!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B9E9E))
            ) {
                Text("Simpan", color = Color.White)
            }
        }
    }
}


@Composable
fun InfoCard(title: String, content: String, onValueChange: (String) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            color = Color(0xFF2B9E9E),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = content,
            onValueChange = onValueChange,
            label = { Text(title) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }
}
