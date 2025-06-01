    package com.bimo0064.project.Screens

    import android.content.Context
    import android.net.Uri
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.PaddingValues
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.material3.TopAppBar
    import androidx.compose.material3.TopAppBarDefaults
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavHostController
    import coil.compose.rememberAsyncImagePainter
    import com.bimo0064.project.model.DataPerpanjangKost
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.draw.shadow
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.unit.sp
    import coil.compose.AsyncImagePainter.State.Empty.painter
    import com.bimo0064.project.R

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DataPerpanjangKostScreen(navController: NavHostController) {
        val context = LocalContext.current
        var data by remember { mutableStateOf(loadData(context)) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Detail Perpanjangan Kost",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "Kembali",
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable { navController.popBackStack() }
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF2B9E9E)
                    )
                )
            },
            containerColor = Color(0xFFF9FBFB)
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (data != null) {
                    Text("Nama", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(data!!.nama, style = MaterialTheme.typography.bodyLarge)

                    Text("Kamar", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(data!!.kamar, style = MaterialTheme.typography.bodyLarge)

                    Text("Bulan", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(data!!.bulan, style = MaterialTheme.typography.bodyLarge)

                    if (!data!!.imageUri.isNullOrEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(Uri.parse(data!!.imageUri)),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .padding(top = 8.dp)
                                .background(Color.White, shape = MaterialTheme.shapes.medium)
                                .shadow(4.dp, shape = MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Color.LightGray, shape = MaterialTheme.shapes.medium),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Tidak ada gambar", color = Color.DarkGray)
                        }
                    }
                } else {
                    Text("Tidak ada data yang dikirim.", style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B9E9E))
                ) {
                    Text("Kembali", color = Color.White)
                }
            }
        }
    }


    private fun loadData(context: Context): DataPerpanjangKost? {
        val sharedPreferences = context.getSharedPreferences("KostData", Context.MODE_PRIVATE)
        val nama = sharedPreferences.getString("nama", null)
        val kamar = sharedPreferences.getString("kamar", null)
        val bulan = sharedPreferences.getString("bulan", null)
        val imageUri = sharedPreferences.getString("imageUri", null)

        return if (nama != null && kamar != null && bulan != null && imageUri != null) {
            DataPerpanjangKost(nama, kamar, bulan, imageUri)
        } else {
            null
        }
    }