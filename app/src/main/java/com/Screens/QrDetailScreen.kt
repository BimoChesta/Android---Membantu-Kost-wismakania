package com.bimo0064.project.Screens

import android.net.Uri
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
    var kamar by remember { mutableStateOf("1") }
    var sudahBayar by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> imageUri = uri }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Pembayaran") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2B9E9E),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            InfoCard(title = "Nama", content = nama, onValueChange = { nama = it })
            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown kamar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Kamar",
                    color = Color(0xFF2B9E9E),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box {
                    OutlinedTextField(
                        value = kamar,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Kamar") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        (1..7).forEach { number ->
                            DropdownMenuItem(
                                text = { Text("Kamar $number") },
                                onClick = {
                                    kamar = number.toString()
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Upload bukti gambar
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Bukti Transfer",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = { imageLauncher.launch("image/*") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Upload Bukti")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox Sudah Bayar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp)
                    .toggleable(
                        value = sudahBayar,
                        onValueChange = { sudahBayar = it }
                    )
            ) {
                Checkbox(
                    checked = sudahBayar,
                    onCheckedChange = { sudahBayar = it }
                )
                Text(text = "Sudah Bayar", modifier = Modifier.padding(start = 8.dp))
            }

            // Tombol Simpan
            Button(
                onClick = {
                    if (nama.isNotBlank() && kamar.isNotBlank()) {
                        val pembayaran = Pembayaran(
                            nama = nama,
                            kamar = kamar,
                            bukti = if (sudahBayar) "Sudah Bayar" else "Belum Bayar",
                            imageUri = imageUri?.toString()
                        )
                        scope.launch {
                            dataStoreManager.savePayment(pembayaran)
                            nama = ""
                            kamar = "1"
                            sudahBayar = false
                            imageUri = null
                            navController.navigateUp()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(0.6f)
            ) {
                Text(text = "Simpan", color = Color.White)
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
