package com.example.marinegame.home

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.example.marinegame.R
import com.example.marinegame.RulesActivity
import com.example.marinegame.adapter.PlayersAdapter
import com.example.marinegame.model.Player
import com.example.marinegame.model.Role
import com.example.marinegame.play.GameActivity
import android.widget.Toast
import android.content.Context


class HomeActivity : AppCompatActivity(), HomeContract.MvpView, PlayersAdapter.onPlayerListener {

    lateinit var mPresenter : HomePresenter
    lateinit var playersRecyclerView : RecyclerView
    lateinit var playerField : EditText
    lateinit var playersList : ArrayList<Player>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mPresenter = HomePresenter(this)

        playerField = findViewById(R.id.player_name_edittext)
        playersRecyclerView = findViewById(R.id.player_list)
        playersList = ArrayList()

        playersRecyclerView.layoutManager = LinearLayoutManager(this)
        playersRecyclerView.adapter = PlayersAdapter(playersList, this)

        loadRulesFirstTime()

    }

    override fun onPlayerClick(position: Int) {
        playersList.removeAt(position)
        playersRecyclerView.adapter.notifyDataSetChanged()
    }

    fun play(view : View) {
        if(playersList.size < 2) {
            Toast.makeText(this,"3 joueurs minimum", Toast.LENGTH_LONG).show()
        }
        else {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("playersList", playersList)
            startActivity(intent)
        }
    }

    fun openRules(view : View) {
        startActivity(Intent(this, RulesActivity::class.java))
    }

    fun addPlayer(view : View){

        for(player in playersList) {
            if(player.name.toUpperCase().equals(playerField.text.toString().toUpperCase())) {
                Toast.makeText(this, "Joueur déjà existant", Toast.LENGTH_LONG).show()
                return;
            }
        }

        if(playersList.size > 15) {
            Toast.makeText(this,"15 joueurs maximum", Toast.LENGTH_LONG).show()
        }

        else if(!playerField.text.isEmpty()) {
            playersList.add(Player(playerField.text.toString(), Role("", "")))
            playersRecyclerView.adapter.notifyDataSetChanged()
            playerField.text.clear()
        }
    }

    fun loadRulesFirstTime() {
        val isFirstRun = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            .getBoolean("isFirstRun", true)

        if (isFirstRun) {
            startActivity(Intent(this, RulesActivity::class.java))
        }

        getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
            .putBoolean("isFirstRun", false).commit()
    }
}
