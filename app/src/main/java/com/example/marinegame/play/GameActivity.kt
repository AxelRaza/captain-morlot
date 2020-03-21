package com.example.marinegame.play

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.marinegame.R
import com.example.marinegame.RulesActivity
import com.example.marinegame.endgame.EndGameActivity
import com.example.marinegame.model.Player
import kotlin.random.Random
import android.os.Handler


class GameActivity : AppCompatActivity(), GameContract.MvpView {

    lateinit var randomWord : TextView
    lateinit var presenter : GameContract.Presenter
    lateinit var roleDialog : Dialog
    lateinit var playersList : ArrayList<Player>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        randomWord = findViewById(R.id.random_word_textview)
        presenter = GamePresenter(this, GetWordIntractorImpl())
        presenter.requestDataFromServer()
        playersList = intent.extras["playersList"] as ArrayList<Player>
        showRoleDialog()
    }

    fun backToHome(view: View) {
        finish()
    }

    fun openHelp(view : View) {
        startActivity(Intent(this,RulesActivity::class.java))
    }

    fun onClickScreen(view: View) {
        val gameIntent = Intent(this, EndGameActivity::class.java)
        gameIntent.putExtra("playersList", playersList)
        startActivity(gameIntent)
        finish()
    }

    fun showRoleDialog() {
        roleDialog = Dialog(this)
        roleDialog.setContentView(R.layout.roles_popup)

        val okButton = roleDialog.findViewById<Button>(R.id.ok_button)
        val playerTurn = roleDialog.findViewById<TextView>(R.id.popup_textview)
        val piratePlayerText = roleDialog.findViewById<TextView>(R.id.player_pirate_name_textview)
        val moussaillonPlayerText = roleDialog.findViewById<TextView>(R.id.player_mous_name_textview)
        val moussaillonDescText = roleDialog.findViewById<TextView>(R.id.moussaillon_textview)

        var pirateIndex = 0
        var moussaillonIndex = 0

        for(player in playersList) {
            if(player.role.name == "Moussaillon")
                player.role.name = ""
        }

        do {
            pirateIndex = Random.nextInt(0, playersList.size)
            moussaillonIndex = Random.nextInt(0, playersList.size)
        }
        while (pirateIndex == moussaillonIndex)

        if(playersList.size < 3 && isPirate()) {
            playersList[pirateIndex].role.name = "Pirate"
            playersList[moussaillonIndex].role.name = ""
        }
        else if(playersList.size > 2 && isPirate()) {
            playersList[moussaillonIndex].role.name = "Moussaillon"
        }
        else if(playersList.size < 3 && !isPirate()) {
            playersList[pirateIndex].role.name = "Pirate"
            playersList[moussaillonIndex].role.name = ""
        }
        else if(playersList.size > 2 && !isPirate()){
            playersList[pirateIndex].role.name = "Pirate"
            playersList[moussaillonIndex].role.name = "Moussaillon"
        }

        for(player : Player in playersList) {
            if(player.role.name.equals("Matelot") || player.role.name.equals(""))
                player.role.name = "Matelot"
        }

        for(player in playersList) {
            if(player.role.name == "Pirate")
                piratePlayerText.text = player.name
            else if(player.role.name == "Moussaillon")
                moussaillonPlayerText.text = player.name
        }

        if(!isMoussaillon()) {
            moussaillonPlayerText.visibility = View.GONE
            moussaillonDescText.visibility = View.GONE
        }

        playerTurn.text = playersList[Random.nextInt(0, playersList.size)].name + " commence en premier"

        okButton.setOnClickListener {
            roleDialog.dismiss()
        }
        roleDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val run = Runnable { roleDialog.show() }
        Handler().postDelayed(run, 500)
    }

    override fun onResponseFailure(throwable: Throwable) {
        Toast.makeText(this,
            "Oups, une erreur est survenue commandant",
            Toast.LENGTH_LONG).show();
    }

    override fun generateWord(word: String) {
        randomWord.text = word
    }

    fun isPirate(): Boolean {
        for(player in playersList) {
            if(player.role.name.equals("Pirate"))
                return true

        }
        return false
    }

    fun isMoussaillon() : Boolean {
        for(player in playersList) {
            if(player.role.name.equals("Moussaillon"))
                return true
        }
        return false
    }
}
