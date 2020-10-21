package com.example.gamelist

data class GameModel(
        val id: Int,
        val name: String,
        val picture: String,
        val type: String,
        val players: Int,
        val year: Int,
        val url: String,
        val description_en: String)