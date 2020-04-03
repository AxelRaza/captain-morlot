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
import android.widget.Toast
import com.example.marinegame.model.Game
import com.example.marinegame.model.SharedPreferenciesManager
import com.example.marinegame.properties.GameProperties


class HomeActivity : AppCompatActivity(), HomeContract.MvpView, PlayersAdapter.onPlayerListener {

    lateinit var mPresenter : HomePresenter
    lateinit var playersRecyclerView : RecyclerView
    lateinit var playerField : EditText
    val game : Game = Game.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mPresenter = HomePresenter(this, game)
        playerField = findViewById(R.id.player_name_edittext)
        playersRecyclerView = findViewById(R.id.player_list)

        playersRecyclerView.layoutManager = LinearLayoutManager(this)
        playersRecyclerView.adapter = PlayersAdapter(game.playersList(), this)
        loadRulesFirstTime()

    }

    override fun onPlayerClick(position: Int) {
        mPresenter.removePlayerAt(position)
    }

    fun play(view : View) {
        if(game.playersList().size < Game.NB_MIN_PLAYERS)
            Toast.makeText(this,Game.NB_MIN_PLAYERS.toString() + " joueurs minimum", Toast.LENGTH_LONG).show()
        else {
            val intent = Intent(this, GameProperties::class.java)
            intent.putExtra(Game.GAME_DATA, game)
            startActivity(intent)
            overridePendingTransition(R.anim.exit_2, R.anim.entry_2)
        }
    }

    fun openRules(view : View) {
        startActivity(Intent(this, RulesActivity::class.java))
        overridePendingTransition(R.anim.exit, R.anim.entry)
    }

    fun addPlayer(view : View){

        if(game.exist(playerField.text.toString()))
            Toast.makeText(this, "Joueur déjà existant", Toast.LENGTH_LONG).show()

        if(game.playersList().size == Game.NB_MAX_PLAYERS)
            Toast.makeText(this,Game.NB_MAX_PLAYERS.toString() + " joueurs maximum", Toast.LENGTH_LONG).show()

        else if(!playerField.text.isEmpty()) {
            mPresenter.addPlayer(playerField.text.toString(), "")
            playerField.text.clear()
        }
    }

    fun loadRulesFirstTime() {
        if (SharedPreferenciesManager.isFirstRunApp(this))
            startActivity(Intent(this, RulesActivity::class.java))
        SharedPreferenciesManager.setFirstRunAppToFalse(this)
    }

    override fun updatePlayersList() {
        playersRecyclerView.adapter.notifyDataSetChanged()
    }
}
