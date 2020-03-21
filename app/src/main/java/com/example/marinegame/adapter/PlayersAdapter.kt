package com.example.marinegame.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.marinegame.R
import com.example.marinegame.model.Player

class PlayersAdapter(private val players : ArrayList<Player>, private val mPlayerListener: onPlayerListener) : RecyclerView.Adapter<PlayersAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.players_list_case, parent, false)
        return MyViewHolder(view, mPlayerListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val playersList = players[position]
        holder.display(playersList)
    }

    inner class MyViewHolder(itemView: View, onPlayerListener: onPlayerListener) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView

        init {

            name = itemView.findViewById(R.id.player_name_textview)

            itemView.setOnClickListener {
                onPlayerListener.onPlayerClick(adapterPosition)
            }
        }

        fun display(mPlayer: Player) {
            name.text = mPlayer.name
        }
    }

    interface onPlayerListener {
        fun onPlayerClick(position: Int)
    }

}