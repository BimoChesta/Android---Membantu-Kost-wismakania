package com.bimo0064.project

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Screens.InformasiScreen
import com.Screens.KelolaDataPListrikScreen
import com.Screens.TambahPenghuniScreen
import com.bimo0064.project.Screens.HomeScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppEntryPoint()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppEntryPoint() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(onMenuClick = { route ->
                navController.navigate(route)
                scope.launch { drawerState.close() }
            })
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Wisma Kania",
                                fontSize = 20.sp,
                                color = Color.Black,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Blue
                    )
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                AppNavGraph(navController = navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("informasi") { InformasiScreen() }
        composable("tambah") { TambahPenghuniScreen() }
        composable("listrik") { KelolaDataPListrikScreen() }
    }
}

@Composable
fun DrawerContent(onMenuClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight() // Mengisi tinggi
            .width(300.dp) // Mengatur lebar drawer menjadi setengah
            .padding(0.dp)
            .background(Color.White)
    ) {
        // Header Logo dan Judul
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logobiasa),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Wisma Kania",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color(0xFF2B9E9E),
                    fontSize = 20.sp
                )
            )
        }

        Text(
            text = "Menu",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        DrawerItem("Informasi Kost", Icons.Default.Info) { onMenuClick("informasi") }
        DrawerItem("Tambah Penghuni", Icons.Default.Info) { onMenuClick("tambah") }
        DrawerItem("Kelola Data P.Listrik", Icons.Default.Info) { onMenuClick("listrik") }
        DrawerItem("Logout", Icons.Default.ExitToApp) { onMenuClick("home") }
    }
}

@Composable
fun DrawerItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color(0xFF2B9E9E),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}