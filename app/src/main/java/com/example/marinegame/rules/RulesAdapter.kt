package com.oc.rss.oc_rss

import android.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.marinegame.R

import java.util.Arrays

class RulesAdapter : RecyclerView.Adapter<RulesAdapter.MyViewHolder>() {

    private val roles = Arrays.asList(
       Pair.create("Le Matelot", "Le bon camarade qui obéit comme un chien"),
        Pair.create("Le Pirate", "Aussi méchant que le capitaine Crochet"),
        Pair.create("Le Moussaillon", "Le débutant de seconde zone qui va ruiner ta partie")
    )

    override fun getItemCount(): Int {
        return roles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rules_list_case, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pair = roles[position]
        holder.display(pair)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val role: TextView
        private val description: TextView

        private var currentPair: Pair<String, String>? = null

        init {

            role = itemView.findViewById(R.id.role_textview) as TextView
            description = itemView.findViewById(R.id.role_desc_textview) as TextView

            itemView.setOnClickListener {
                AlertDialog.Builder(itemView.context)
                    .setTitle(currentPair!!.first)
                    .setMessage(currentPair!!.second)
                    .show()
            }
        }

        fun display(pair: Pair<String, String>) {
            currentPair = pair
            role.text = pair.first
            description.text = pair.second
        }
    }

}