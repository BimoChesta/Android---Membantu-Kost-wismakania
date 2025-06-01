package com.bimo0064.project

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bimo0064.project.Screens.AturanScreen
import com.bimo0064.project.Screens.AwalScreen
import com.bimo0064.project.Screens.CekSaldoScreen
import com.bimo0064.project.Screens.DataPerpanjangKostScreen
import com.bimo0064.project.Screens.HomeScreen
import com.bimo0064.project.Screens.HomeScreenAdmin
import com.bimo0064.project.Screens.InformasiScreen
import com.bimo0064.project.Screens.KasScreen
import com.bimo0064.project.Screens.KeduaScreen
import com.bimo0064.project.Screens.KelolaDataListrikScreen
import com.bimo0064.project.Screens.LoginScreen
import com.bimo0064.project.Screens.PerpanjangKostScreen
import com.bimo0064.project.Screens.QrDetailScreen
import com.bimo0064.project.Screens.RegisterScreen
import com.bimo0064.project.Screens.RiwayatListrik
import com.bimo0064.project.data.DataStoreManager

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

        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { 
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            IconButton(onClick = {
//                                scope.launch { drawerState.open() }
//                            }) {
//                            }
//                        }
//                    },
//                    )
//            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                AppNavGraph(navController = navController, dataStoreManager = dataStoreManager)
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController, dataStoreManager: DataStoreManager) {
    NavHost(navController = navController, startDestination = "home") {

        composable("awalan") {
            AwalScreen(navController)
        }

        composable("kedua") {
            KeduaScreen(navController)
        }

        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController, username ->
                    if (username == "admin") {
                        navController.navigate("homeAdmin") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                navController = navController,
                dataStoreManager = dataStoreManager
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                dataStoreManager = dataStoreManager,
                navController = navController
            )
        }

        composable("home") {
            HomeScreen(navController, dataStoreManager)
        }

        composable("homeAdmin") {
            HomeScreenAdmin(navController, dataStoreManager)
        }

        composable("informasi") {
            InformasiScreen()
        }

        composable("cek_saldo") {
            CekSaldoScreen(dataStoreManager)
        }

        composable("kas") {
            KasScreen(dataStoreManager)
        }

        composable("listrik") {
            KelolaDataListrikScreen(onBackPressed = { navController.popBackStack() })
        }

        composable("aturan") {
            AturanScreen()
        }

        composable("riwayatlistrik") {
            RiwayatListrik()
        }

        composable("qr_detail") {
            QrDetailScreen(navController, dataStoreManager)
        }

        composable("perpanjangKost") {
            PerpanjangKostScreen(navController)
        }

        composable("dataPerpanjangKost") {
            DataPerpanjangKostScreen(navController)
        }
    }
}