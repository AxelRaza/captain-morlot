package com.oc.rss.oc_rss

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.marinegame.R

import java.util.Arrays

class RulesAdapter(private val roles : List<Pair<String, String>>, private val monRoleListener: onRoleListener) : RecyclerView.Adapter<RulesAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return roles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rules_list_case, parent, false)
        return MyViewHolder(view, monRoleListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pair = roles[position]
        holder.display(pair)
    }

    inner class MyViewHolder(itemView: View, onRoleListener: onRoleListener) : RecyclerView.ViewHolder(itemView) {

        private val role: TextView
        private val description: TextView

        private var currentPair: Pair<String, String>? = null

        init {

            role = itemView.findViewById(R.id.role_textview) as TextView
            description = itemView.findViewById(R.id.role_desc_textview) as TextView

            itemView.setOnClickListener {
                onRoleListener.onRoleClick(adapterPosition)
            }
        }

        fun display(pair: Pair<String, String>) {
            currentPair = pair
            role.text = pair.first
            description.text = pair.second
        }
    }

    interface onRoleListener {
        fun onRoleClick(position: Int)
    }

}