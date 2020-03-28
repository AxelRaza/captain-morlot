package com.example.marinegame.endgame

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.widget.Button
import android.widget.GridLayout
import com.example.marinegame.R
import com.example.marinegame.model.Player
import com.example.marinegame.play.GameActivity
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.marinegame.model.Game
import kotlin.random.Random


class EndGameActivity : AppCompatActivity(), EndGameContract.MvpView {

    lateinit var playerCases : ArrayList<View>
    lateinit var grid : GridLayout
    lateinit var endDialog : BottomSheetDialog
    lateinit var game : Game
    lateinit var presenter: EndGamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        grid = findViewById(R.id.grid)
        game = intent.extras[Game.GAME_DATA] as Game
        presenter = EndGamePresenter(this, game)
        playerCases = ArrayList()
        presenter.showPlayersList()

    }

    fun showConfirmDialog(player : Player, case : View, view : View) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(player.name + " (" + player.role + ")")
        builder.setMessage("Est-ce votre dernier mot ?")
        builder.setPositiveButton("Oui") {
                dialog, which ->
            if(case.id == view.id) {
                presenter.updateGame(player)
            }

        }
        builder.setNegativeButton("Non") {
                dialog, which ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun showEndDialog(player : Player) {
        endDialog = BottomSheetDialog(this, R.style.SheetDialog)
        endDialog.setContentView(R.layout.endgame_popup)

        val playerWin = endDialog.findViewById<TextView>(R.id.player_winner_textview)
        val endButton = endDialog.findViewById<Button>(R.id.end_button)

        playerWin!!.text = player.name

        when(player.role) {
            Game.MATELOT -> playerWin.setTextColor(resources.getColor(R.color.green))
            Game.PIRATE -> playerWin.setTextColor(resources.getColor(R.color.orange))
            Game.MOUSSAILLON -> playerWin.setTextColor(resources.getColor(R.color.blue))
        }

        endButton!!.setOnClickListener {
            finish()
        }

        endDialog.show()

        endDialog.setOnDismissListener {
            finish()
        }
    }

    override fun updateDesignPlayer(player: Player, position : Int) {
        val image = View.inflate(this, R.layout.player_end_game_case, null)
        val playerImage = image.findViewById<TextView>(R.id.player_image_textview)
        val playerName = image.findViewById<TextView>(R.id.player_name_image)
        playerCases.add(image)

        playerImage.text = player.name.subSequence(0,1)
        playerName.text = player.name

        when(player.role) {
            Game.MATELOT -> playerImage.setBackgroundResource(R.drawable.matelot_background)
            Game.PIRATE -> playerImage.setBackgroundResource(R.drawable.pirate_background)
            Game.MOUSSAILLON -> playerImage.setBackgroundResource(R.drawable.moussaillon_background)
        }

        image.setOnClickListener {
            showConfirmDialog(player,playerCases[position],it)
        }

        grid.addView(image)
    }

    override fun updateGameView() {
        val gameIntent = Intent(this, GameActivity::class.java)
        gameIntent.putExtra(Game.GAME_DATA, game)
        startActivity(gameIntent)
        overridePendingTransition(R.anim.exit,R.anim.entry)
        finish()
    }

    override fun updateEndGame() {
        showEndDialog(game.getWinner())
    }
}
