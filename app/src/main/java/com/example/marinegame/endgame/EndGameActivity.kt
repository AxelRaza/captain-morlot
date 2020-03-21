package com.example.marinegame.endgame

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import com.example.marinegame.R
import com.example.marinegame.model.Player
import com.example.marinegame.play.GameActivity
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.TextView
import kotlin.random.Random


class EndGameActivity : AppCompatActivity() {

    lateinit var playersList : ArrayList<Player>
    lateinit var buttons : ArrayList<Button>
    lateinit var grid : GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        grid = findViewById(R.id.grid)
        playersList = intent.extras["playersList"] as ArrayList<Player>
        buttons = ArrayList()

        for(i in 0..playersList.size-1) {
            buttons.add(Button(this))
            val params = GridLayout.LayoutParams()
            params.setMargins(30,30,30,30)
            params.height = 120
            params.width = 120
            buttons[i].id = i
            buttons[i].text = playersList[i].name.toUpperCase().subSequence(0,1)
            buttons[i].textSize = 50F
            buttons[i].setBackgroundColor(resources.getColor(R.color.colorPrimary))
            buttons[i].layoutParams = params

            buttons[i].setOnClickListener {
               showConfirmDialog(playersList[i],buttons[i],it)
            }

            grid.addView(buttons[i],i)
        }

    }

    fun showConfirmDialog(player : Player, button : Button, view : View) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(player.name + " (" + player.role.name + ")")
        builder.setMessage("Est-ce votre dernier mot ?")
        builder.setPositiveButton("Oui") {
                    dialog, which ->
                if(button.id == view.id) {
                    if(playersList.size > 2) {
                        playersList.removeAt(button.id)
                        val gameIntent = Intent(this, GameActivity::class.java)
                        gameIntent.putExtra("playersList", playersList)
                        startActivity(gameIntent)
                        finish()
                    }
                    else if(playersList.size == 2) {
                        playersList.removeAt(button.id)
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
        val roleDialog = Dialog(this)
        roleDialog.setContentView(R.layout.endgame_popup)

        val playerWin = roleDialog.findViewById<TextView>(R.id.player_winner_textview)
        val endButton = roleDialog.findViewById<Button>(R.id.end_button)

        playerWin.text = player.name + " le " + player.role.name

        endButton.setOnClickListener {
            finish()
        }

        roleDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        roleDialog.show()
    }
}
