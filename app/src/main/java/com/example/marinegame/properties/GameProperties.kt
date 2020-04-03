package com.example.marinegame.properties

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import com.example.marinegame.R
import com.example.marinegame.model.Game
import com.example.marinegame.play.GameActivity

class GameProperties : AppCompatActivity(), GamePropertiesContract.MvpView {

    lateinit private var pirate_seekbar : SeekBar
    lateinit private var moussaillon_seekbar : SeekBar
    lateinit private var pirate : TextView
    lateinit private var moussaillon : TextView
    lateinit private var matelot : TextView
    lateinit private var game: Game
    lateinit private var presenter: GamePropertiesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_properties)
        game = intent.extras[Game.GAME_DATA] as Game
        presenter = GamePropertiesPresenter(this, game)
        pirate_seekbar = findViewById(R.id.pirate_seekbar)
        moussaillon_seekbar = findViewById(R.id.moussaillon_seekbar)
        pirate = findViewById(R.id.pirate_textview)
        moussaillon = findViewById(R.id.moussaillon_textview)
        matelot = findViewById(R.id.matelot_textview)
        presenter.updateRoles()
    }

    override fun showRolesMinPlayer(progressMax: Int, nbPlayers: Int) {
        pirate_seekbar.max = progressMax
        moussaillon_seekbar.max = progressMax
        matelot.text = (nbPlayers - 2).toString() + " Matelots"

        pirate_seekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var matelots = nbPlayers - moussaillon_seekbar.progress/100 - progress/100
                matelot.text = matelots.toString() + " Matelots"
                pirate.text = (progress/100).toString() + " " + Game.PIRATE
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        moussaillon_seekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val matelots = nbPlayers - pirate_seekbar.progress/100 - progress/100
                matelot.text = matelots.toString() + " Matelots"
                moussaillon.text = (progress/100).toString() + " " + Game.MOUSSAILLON
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }


    override fun showRoles(progressMax: Int, nbPlayers : Int) {
        pirate_seekbar.max = progressMax
        moussaillon_seekbar.max = progressMax
        matelot.text = (nbPlayers - 2).toString() + " Matelots"

        pirate_seekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var matelots = nbPlayers - moussaillon_seekbar.progress/100 - progress/100
                if(progress == progressMax)
                    moussaillon_seekbar.max = progressMax - 100
                else
                    moussaillon_seekbar.max = progressMax
                matelot.text = matelots.toString() + " Matelots"
                if(progress/100 > 1)
                    pirate.text = (progress/100).toString() + " " + Game.PIRATES
                else
                    pirate.text = (progress/100).toString() + " " + Game.PIRATE
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        moussaillon_seekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val matelots = nbPlayers - pirate_seekbar.progress/100 - progress/100
                if(progress == progressMax)
                    pirate_seekbar.max = progressMax - 100
                else
                    pirate_seekbar.max = progressMax
                matelot.text = matelots.toString() + " Matelots"
                if(progress/100 > 1)
                    moussaillon.text = (progress/100).toString() + " " + Game.MOUSSAILLONS
                else
                    moussaillon.text = (progress/100).toString() + " " + Game.MOUSSAILLON
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    override fun hideRoles(nbPlayers: Int) {
        pirate.visibility = View.GONE
        pirate_seekbar.visibility = View.GONE
        moussaillon.visibility = View.GONE
        moussaillon_seekbar.visibility = View.GONE
        matelot.text = nbPlayers.toString() + " Matelots"
    }

    fun launchGame(view : View) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(Game.GAME_DATA, game)
        intent.putExtra(Game.NB_PIRATES, pirate_seekbar.progress/100)
        intent.putExtra(Game.NB_MOUSSAILLONS, moussaillon_seekbar.progress/100)
        startActivity(intent)
        overridePendingTransition(R.anim.exit_2, R.anim.entry_2)
        finish()
    }
}
