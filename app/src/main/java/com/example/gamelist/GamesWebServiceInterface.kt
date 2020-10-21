package com.example.gamelist

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesWebServiceInterface {
    @GET("game/list")
    fun getGamesList(): Call<List<GameModel>>

    @GET("game/details")
    fun getGamesDetails(@Query("game_id") gameId : Int) : Call<GameModel>
}