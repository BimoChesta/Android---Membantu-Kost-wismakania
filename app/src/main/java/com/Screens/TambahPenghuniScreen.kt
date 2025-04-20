package com.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahPenghuniScreen() {
    val namaLengkap = remember { mutableStateOf(TextFieldValue()) }
    val noKamar = remember { mutableStateOf(TextFieldValue()) }
    val tanggalPembayaran = remember { mutableStateOf(TextFieldValue()) }
    val username = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val konfirmasiPassword = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Tambah Penghuni",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = namaLengkap.value,
            onValueChange = { namaLengkap.value = it },
            label = { Text("Masukkan Nama Lengkap") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        TextField(
            value = noKamar.value,
            onValueChange = { noKamar.value = it },
            label = { Text("Masukkan No. Kamar") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        TextField(
            value = tanggalPembayaran.value,
            onValueChange = { tanggalPembayaran.value = it },
            label = { Text("Masukkan Tanggal Pembayaran Listrik") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Masukkan Username") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Masukkan Password") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        TextField(
            value = konfirmasiPassword.value,
            onValueChange = { konfirmasiPassword.value = it },
            label = { Text("Konfirmasi Password") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = { /* Tindakan untuk menambahkan penghuni */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tambahkan Penghuni")
        }
    }
}