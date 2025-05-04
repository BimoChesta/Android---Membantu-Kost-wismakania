package com.bimo0064.project.model

import java.io.Serializable

data class DataPerpanjangKost(
    val nama: String,
    val kamar: String,
    val bulan: String,
    val imageUri: String?
) : Serializable
