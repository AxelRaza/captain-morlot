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
import kotlin.random.Random


class EndGameActivity : AppCompatActivity() {

    lateinit var playersList : ArrayList<Player>
    lateinit var playerCases : ArrayList<View>
    lateinit var grid : GridLayout
    lateinit var endDialog : BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        grid = findViewById(R.id.grid)
        playersList = intent.extras["playersList"] as ArrayList<Player>
        playerCases = ArrayList()

        for(i in 0..playersList.size-1) {
            val image = View.inflate(this, R.layout.player_end_game_case, null)
            val playerImage = image.findViewById<TextView>(R.id.player_image_textview)
            val playerName = image.findViewById<TextView>(R.id.player_name_image)
            playerCases.add(image)

            playerImage.text = playersList[i].name.subSequence(0,1)
            playerName.text = playersList[i].name

            if(playersList[i].role.name.equals("Matelot")) {
                playerImage.setBackgroundResource(R.drawable.matelot_background)
            }
            else if(playersList[i].role.name.equals("Pirate")) {
                playerImage.setBackgroundResource(R.drawable.pirate_background)
            }
            else if(playersList[i].role.name.equals("Moussaillon")) {
                playerImage.setBackgroundResource(R.drawable.moussaillon_background)
            }

            image.setOnClickListener {
                showConfirmDialog(playersList[i],playerCases[i],it)
            }

           grid.addView(image)


        }

    }

    fun showConfirmDialog(player : Player, case : View, view : View) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(player.name + " (" + player.role.name + ")")
        builder.setMessage("Est-ce votre dernier mot ?")
        builder.setPositiveButton("Oui") {
                dialog, which ->
            if(case.id == view.id) {
                if(playersList.size > 2) {
                    playersList.remove(player)
                    val gameIntent = Intent(this, GameActivity::class.java)
                    gameIntent.putExtra("playersList", playersList)
                    startActivity(gameIntent)
                    overridePendingTransition(R.anim.exit,R.anim.entry)
                    finish()
                }
                else if(playersList.size == 2) {
                    playersList.remove(player)
                    showEndDialog(playersList.first())
                }

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

        playerWin!!.text = player.name + " le " + player.role.name

        when(player.role.name) {
            "Matelot" -> playerWin.setTextColor(resources.getColor(R.color.green))
            "Pirate" -> playerWin.setTextColor(resources.getColor(R.color.orange))
            "Moussaillon" -> playerWin.setTextColor(resources.getColor(R.color.blue))
        }

        endButton!!.setOnClickListener {
            finish()
        }

        endDialog.show()

        endDialog.setOnDismissListener {
            finish()
        }
    }
}
