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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bimo0064.project.Screens.AwalScreen
import com.bimo0064.project.Screens.CekSaldoScreen
import com.bimo0064.project.Screens.HomeScreen
import com.bimo0064.project.Screens.InformasiScreen
import com.bimo0064.project.Screens.KeduaScreen
import com.bimo0064.project.Screens.KelolaDataListrikScreen
import com.bimo0064.project.Screens.LoginScreen
import com.bimo0064.project.Screens.QrDetailScreen
import com.bimo0064.project.Screens.RegisterScreen
import com.bimo0064.project.data.DataStoreManager
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
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }

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
                        containerColor = Color.Cyan
                    )
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                AppNavGraph(navController = navController, dataStoreManager = dataStoreManager)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController, dataStoreManager: DataStoreManager) {
    NavHost(navController = navController, startDestination = "awalan") {
        composable("awalan") { AwalScreen(navController) }
        composable("kedua") { KeduaScreen(navController) }
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") },
                navController = navController,
                dataStoreManager = dataStoreManager
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("login") },
                dataStoreManager = dataStoreManager,
                navController = navController
            )
        }
        composable("home") { HomeScreen(navController, dataStoreManager) }
        composable("informasi") { InformasiScreen() }
        composable("cek_saldo") { CekSaldoScreen(dataStoreManager) }
        composable("listrik") { KelolaDataListrikScreen() }
        composable("qr_detail") { QrDetailScreen(navController, dataStoreManager) }
    }
}

@Composable
fun DrawerContent(onMenuClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .padding(0.dp)
            .background(Color.White)
    ) {
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
        DrawerItem("Kelola Data P.Listrik", Icons.Default.Home) { onMenuClick("listrik") }
        DrawerItem("Cek saldo kas", Icons.Default.Money) { onMenuClick("cek_saldo") }
        DrawerItem("Logout", Icons.Default.ExitToApp) { onMenuClick("login") }
    }
}

@Composable
fun DrawerItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
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
