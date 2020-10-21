package com.example.gamelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        val gameId = intent.getIntExtra("GameId", 0)

        // init RetroFit.
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service: GamesWebServiceInterface = retrofit.create(GamesWebServiceInterface::class.java)


        // API call.
        val callback: Callback<GameModel> = object : Callback<GameModel> {
            override fun onResponse(call: Call<GameModel>, response: Response<GameModel>) {
                if(response.isSuccessful)
                {
                    val game = response.body()!! as GameModel

                    // Set Data.
                    Glide.with(this@GameDetailsActivity).load(game.picture).
                    into(findViewById<ImageView>(R.id.image_game_picture))
                    findViewById<TextView>(R.id.text_game_name).text = game.name
                    findViewById<TextView>(R.id.text_game_type).text = game.type
                    findViewById<TextView>(R.id.text_game_year).text = game.year.toString()
                    findViewById<TextView>(R.id.text_game_players).text = game.players.toString()
                    findViewById<TextView>(R.id.text_game_description).text = game.description_en
                }
            }

            override fun onFailure(call: Call<GameModel>, t: Throwable) {
                Log.e("None", "Woops ...")
            }

        }
        service.getGamesDetails(gameId).enqueue(callback)

    }
}