package com.project.beautifulday

import java.util.UUID

data class Local(
    val idLocal: String? = UUID.randomUUID().toString(),
    val emailUser: String? = "",
    val nombreLocal: String?= "",
    val fotoLocal: String?= "",
    val comentario: String?= "",
    val pais: String?="",
    val ciudad: String?="",
    val web: String?= "",
    val nameUser: String? = "",
    val latitud: Double? = 0.0,
    val longitud: Double? = 0.0,
    val votes: Int? = 0,
    val puntuacion: Double? = 0.0,
    val idDocument: String? = "",
    val listVotes: MutableList<String>? = mutableListOf(),
)
