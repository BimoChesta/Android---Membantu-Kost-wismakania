package com.bimo0064.project.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bimo0064.project.R
import com.bimo0064.project.data.DataStoreManager
import com.bimo0064.project.model.User
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    dataStoreManager: DataStoreManager
) {
    var saldo by remember { mutableStateOf(0) }
    var user by remember { mutableStateOf<User?>(null) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        saldo = dataStoreManager.loadBalance()
        user = dataStoreManager.loadUser()
    }

    val isAdmin = user?.username == "admin" && user?.password == "1234"
    val userName = if (isAdmin) "Admin" else user?.name ?: "Pengguna"

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF6F9F9)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Icon account di kanan atas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { showLogoutDialog = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_account_circle_24),
                        contentDescription = "Logout"
                    )
                }
            }

            // Greeting
            Column {
                Text(
                    text = "Selamat Datang,",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            // Balance + Scan QR
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.dompet),
                            contentDescription = "Dompet",
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Uang Kas",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Rp. $saldo",
                                style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF2E7D32))
                            )
                        }
                    }

                    // Scan QR
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF0F0F0))
                            .clickable { navController.navigate("qr_detail") },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.scan),
                            contentDescription = "QR Code",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                MenuButton(R.drawable.petir, "Listrik", navController, "listrik")
                MenuButton(R.drawable.aturan, "Aturan", navController, "aturan")
                MenuButton(R.drawable.datakas, "Data Kas", navController, "kas")
                MenuButton(R.drawable.perpanjangkost, "Perpanjang", navController, "perpanjangkost")
                MenuButton(R.drawable.home, "Info Kost", navController, "informasi")
            }
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Konfirmasi Logout") },
                text = { Text("Anda ingin log out?") },
                confirmButton = {
                    TextButton(onClick = {
                        scope.launch {
                            dataStoreManager.clearUser()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                        showLogoutDialog = false
                    }) {
                        Text("Ya")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Batal")
                    }
                }
            )
        }
    }
}


@Composable
fun MenuButton(iconRes: Int, title: String, navController: NavHostController, destination: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { navController.navigate(destination) }
            .shadow(5.dp, RoundedCornerShape(16.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 16.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            )
        }
    }
}

