package com.example.listapi.model

data class DataAllItem(
    val appearance: AppearanceX,
    val biography: BiographyX,
    val connections: ConnectionsX,
    val id: Int,
    val images: ImagesX,
    val name: String,
    val powerstats: PowerstatsX,
    val slug: String,
    val work: WorkX
)