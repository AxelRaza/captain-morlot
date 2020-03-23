package com.example.marinegame.play

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
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
import android.util.Log
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout


class GameActivity : AppCompatActivity(), GameContract.MvpView {

    lateinit var view : RelativeLayout
    lateinit var randomWord : TextView
    lateinit var playerFirstTurn : TextView
    lateinit var currentPlayer : Player
    lateinit var presenter : GameContract.Presenter
    lateinit var roleDialog : Dialog
    lateinit var playersList : ArrayList<Player>
    lateinit var backgrounds : IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        randomWord = findViewById(R.id.random_word_textview)
        presenter = GamePresenter(this, GetWordIntractorImpl())
        presenter.requestDataFromServer()
        playersList = intent.extras["playersList"] as ArrayList<Player>
        backgrounds = intArrayOf(R.drawable.blue_background, R.drawable.green_background, R.drawable.orange_background, R.drawable.pink_background, R.drawable.dark_blue_background)

        view = findViewById(R.id.game_view)
        view.setBackgroundResource(backgrounds[Random.nextInt(0,backgrounds.size)])

        currentPlayer = playersList[Random.nextInt(0, playersList.size)]
        playerFirstTurn = findViewById(R.id.player_turn_textview)
        playerFirstTurn.text = "Après " + currentPlayer.name + ", dites un mot en relation avec celui du joueur précédent etc..."

        showRoleDialog()
    }

    fun backToHome(view: View) {
        finish()
    }

    fun openHelp(view : View) {
        startActivity(Intent(this,RulesActivity::class.java))
    }

    fun reloadWord(mView : View) {
        presenter.onRefreshClick()
        var wordAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        randomWord.startAnimation(wordAnim)

        var viewAnimOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        var viewAnimIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        view.startAnimation(viewAnimOut)
        viewAnimOut.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                view.setBackgroundResource(backgrounds[Random.nextInt(0,backgrounds.size)])
                view.startAnimation(viewAnimIn)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

    }

    fun onClickScreen(view: View) {
        val gameIntent = Intent(this, EndGameActivity::class.java)
        gameIntent.putExtra("playersList", playersList)
        startActivity(gameIntent)
        finish()
    }

    fun showRoleDialog() {
        roleDialog = Dialog(this)
        roleDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        roleDialog.setContentView(R.layout.roles_popup)

        val okButton = roleDialog.findViewById<Button>(R.id.ok_button)
        val playerTurn = roleDialog.findViewById<TextView>(R.id.popup_textview)
        val piratePlayerText = roleDialog.findViewById<TextView>(R.id.player_pirate_name_textview)
        val moussaillonPlayerText = roleDialog.findViewById<TextView>(R.id.player_mous_name_textview)
        val moussaillonDescText = roleDialog.findViewById<TextView>(R.id.moussaillon_textview)

        var pirateIndex = 0
        var moussaillonIndex = 1

        for(player in playersList) {
            if(player.role.name == "Moussaillon")
                player.role.name = ""
        }

        playersList.shuffle()

        if(playersList.size > 2)
            playersList[moussaillonIndex].role.name = "Moussaillon"

        if(!isPirate())
            playersList[pirateIndex].role.name = "Pirate"

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

        playerTurn.text = currentPlayer.name + " commence en premier"

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
