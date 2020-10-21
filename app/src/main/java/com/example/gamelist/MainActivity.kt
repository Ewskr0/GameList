package com.example.gamelist

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // init RecyclerView.
        val listGames = findViewById<RecyclerView>(R.id.activity_list_games)
        listGames.setHasFixedSize(true)
        listGames.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
        listGames.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val data = arrayListOf<GameModel>()

        // init RetroFit.
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        val service: GamesWebServiceInterface = retrofit.create(GamesWebServiceInterface::class.java)


        // init ClickListener.
        val gameItemClickListener : View.OnClickListener = View.OnClickListener { clickedView ->
            val gameId = clickedView!!.tag as Int

            // Displays Toast.
            Toast.makeText(this@MainActivity, "Clicked on game item with id $gameId", Toast
                    .LENGTH_SHORT).show()

            // Push GameDetailActivity.
            val intentGameDetail = Intent(this,GameDetailsActivity::class.java).apply {
                putExtra("GameId", gameId)
            }
            startActivity(intentGameDetail)

        }

        // API call.
        val callback: Callback<List<GameModel>> = object : Callback<List<GameModel>> {
            override fun onResponse(call: Call<List<GameModel>>, response: Response<List<GameModel>>) {
                if(response.isSuccessful)
                {
                    listGames.adapter = GameAdapter(response.body()!!, this@MainActivity, gameItemClickListener)
                }
            }

            override fun onFailure(call: Call<List<GameModel>>, t: Throwable) {
                Log.e("None", "Woops...")
            }

        }
        service.getGamesList().enqueue(callback)
    }
}