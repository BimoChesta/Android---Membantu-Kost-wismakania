package com.bimo0064.project.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun HomeScreen(
    navController: NavHostController,
    dataStoreManager: DataStoreManager
) {
    var saldo by remember { mutableIntStateOf(0) }

    // State to hold the User object (can be null)
    var user by remember { mutableStateOf<User?>(null) }

    // Load the user from DataStore only once
    LaunchedEffect(Unit) {
        saldo = dataStoreManager.loadBalance()
        user = dataStoreManager.loadUser()
    }

    // Determine if the user is admin based on the loaded user
    val isAdmin = remember {
        derivedStateOf {
            user?.username == "admin" && user?.password == "1234"
        }
    }

    // Determine the user name based on the loaded user
    val userName = remember {
        derivedStateOf {
            if (isAdmin.value) {
                "Admin"
            } else {
                user?.name ?: "Pengguna"
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF6F9F9)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Bagian Atas: Selamat Datang dan Saldo
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Selamat Datang,",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
                Text(
                    text = userName.value,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dompet),
                        contentDescription = "Saldo Icon",
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Uang kas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Rp. $saldo",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            // Menu Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(200.dp)
            ) {
                item { MenuIcon(R.drawable.petir, "Listrik", navController, "listrik") }
                item { MenuIcon(R.drawable.aturan, "Aturan", navController, "aturan") }
                item { MenuIcon(R.drawable.datakas, "Data kas", navController, "kas") }
                item { MenuIcon(R.drawable.perpanjangkost, "Perpanjang kost", navController, "perpanjangkost") }
                item { MenuIcon(R.drawable.home, "Informasi kost", navController, "informasi") }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.clipboard),
                        contentDescription = "No History",
                        modifier = Modifier.size(90.dp)
                    )
                }
                Text(
                    text = "Anda tidak ada riwayat",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }


            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(Color.White, shape = CircleShape)
                        .shadow(4.dp, shape = CircleShape)
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
    }
}

@Composable
fun MenuIcon(icon: Int, text: String, navController: NavHostController, route: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { navController.navigate(route) }
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = text,
            modifier = Modifier.size(32.dp),
            tint = Color.Unspecified
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}