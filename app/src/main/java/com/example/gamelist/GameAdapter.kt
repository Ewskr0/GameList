package com.example.gamelist

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GameAdapter(private val data : List<GameModel>, private val context: Activity, private val
onItemClickListener: View.OnClickListener) :
        RecyclerView
.Adapter<GameAdapter.ViewHolder>()  {
    class  ViewHolder(private val rowView: View) : RecyclerView.ViewHolder(rowView) {
        val gamePicture : ImageView = itemView.findViewById(R.id.row_game_picture)
        val gameTitle : TextView = itemView.findViewById(R.id.row_game_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView = LayoutInflater.from(context).inflate(R.layout.row_game, parent, false)
        rowView.setOnClickListener(onItemClickListener)

        return ViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(data[position].picture).into(holder.gamePicture)
        holder.gameTitle.text = data[position].name
        holder.itemView.tag = data[position].id
    }

    override fun getItemCount(): Int {
        return data.size
    }
}