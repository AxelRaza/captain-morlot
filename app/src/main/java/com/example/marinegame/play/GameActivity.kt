package com.example.marinegame.play

import android.app.Dialog
import android.content.Intent
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
import android.support.design.widget.BottomSheetDialog
import android.text.Html
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import com.example.marinegame.model.Game


class GameActivity : AppCompatActivity(), GameContract.MvpView {

    lateinit var view : RelativeLayout
    lateinit var randomWord : TextView
    lateinit var playerFirstTurn : TextView
    lateinit var presenter : GameContract.Presenter
    lateinit var roleDialog : Dialog
    lateinit var backgrounds : IntArray
    lateinit var game : Game
    lateinit var playerTurn : TextView
    lateinit var piratePlayerText : TextView
    lateinit var piratePlayerDesc : TextView
    lateinit var matelotPlayerText : TextView
    lateinit var moussaillonPlayerText : TextView
    lateinit var moussaillonDescText : TextView
    lateinit var okButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        game = intent.extras[Game.GAME_DATA] as Game
        game.setNbPirates(intent.extras[Game.NB_PIRATES] as Int)
        game.setNbMoussaillons(intent.extras[Game.NB_MOUSSAILLONS] as Int)
        randomWord = findViewById(R.id.random_word_textview)
        presenter = GamePresenter(this, game, GetWordIntractorImpl())
        presenter.requestDataFromServer()
        backgrounds = intArrayOf(R.drawable.blue_background, R.drawable.green_background, R.drawable.orange_background, R.drawable.pink_background, R.drawable.dark_blue_background, R.drawable.green_yellow_background, R.drawable.yellow_background)
        view = findViewById(R.id.game_view)
        view.setBackgroundResource(backgrounds[Random.nextInt(0,backgrounds.size)])
        playerFirstTurn = findViewById(R.id.player_turn_textview)
        showRoleDialog()
    }

    fun backToHome(view: View) {
        finish()
    }

    fun openHelp(view : View) {
        startActivity(Intent(this,RulesActivity::class.java))
        overridePendingTransition(R.anim.exit, R.anim.entry)
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
        gameIntent.putExtra(Game.GAME_DATA, game)
        gameIntent.putExtra(Game.NB_PIRATES, game.getNbPirates())
        gameIntent.putExtra(Game.NB_MOUSSAILLONS, game.getNbMoussaillons())
        startActivity(gameIntent)
        overridePendingTransition(R.anim.exit_2,R.anim.entry_2)
        finish()
    }

    fun showRoleDialog() {
        roleDialog = BottomSheetDialog(this, R.style.SheetDialog)
        roleDialog.setContentView(R.layout.roles_popup)

        playerTurn = roleDialog.findViewById<TextView>(R.id.popup_textview)
        piratePlayerText = roleDialog.findViewById<TextView>(R.id.player_pirate_name_textview)
        piratePlayerDesc = roleDialog.findViewById<TextView>(R.id.pirate_textview)
        matelotPlayerText = roleDialog.findViewById<TextView>(R.id.player_matelot_name_textview)
        moussaillonPlayerText = roleDialog.findViewById<TextView>(R.id.player_mous_name_textview)
        moussaillonDescText = roleDialog.findViewById<TextView>(R.id.moussaillon_textview)
        okButton = roleDialog.findViewById<Button>(R.id.ok_button)

        presenter.rolesInit()
        presenter.rolesAssign()
        presenter.getRandomPlayer()

        okButton.setOnClickListener {
            roleDialog.dismiss()
        }

        val run = Runnable { roleDialog.show() }
        Handler().postDelayed(run, 300)
    }

    override fun onResponseFailure(throwable: Throwable) {
        Toast.makeText(this,
            "Oups, une erreur est survenue commandant",
            Toast.LENGTH_LONG).show();
    }

    override fun generateWord(word: String) {
        randomWord.text = word
    }

    override fun updateRoles() {
        for(player in game.playersList()) {
            if(player.role == Game.PIRATE)
                piratePlayerText.text = player.name + ", " + piratePlayerText.text.toString()
            else if(player.role == Game.MOUSSAILLON)
                moussaillonPlayerText.text = player.name + ", " + moussaillonPlayerText.text.toString()
        }
    }

    override fun updateRolesPopup(player1: Player, player2: Player) {
        if(!game.moussaillonExist()) {
            moussaillonPlayerText.visibility = View.GONE
            moussaillonDescText.visibility = View.GONE
        }
        if(!game.pirateExist()) {
            piratePlayerText.visibility = View.GONE
            piratePlayerDesc.visibility = View.GONE
        }
        if(!game.moussaillonExist() && !game.pirateExist() && game.getNbPlayers() < 3) {
            matelotPlayerText.setTextColor(resources.getColor(R.color.black))
            matelotPlayerText.text = Html.fromHtml("Duel entre <font color=#96e6a1>" + player1.name + "</font> et <font color=#96e6a1>" + player2.name + "</font> ! Vous êtes matelots et vous ne pouvez que décrire un mot")
        }
    }

    override fun updateFirstPlayer(player: Player) {
        playerFirstTurn.text = "Après " + player.name + ", dites un mot en relation avec celui du joueur précédent etc..."
        when(player.role) {
            Game.PIRATE -> playerTurn.text = Html.fromHtml("<font color=#f09819>" + player.name + "</font> commence en premier")
            Game.MOUSSAILLON -> playerTurn.text = Html.fromHtml("<font color=#4facfe>" + player.name + "</font> commence en premier")
            Game.MATELOT -> playerTurn.text = Html.fromHtml("<font color=#96e6a1>" + player.name + "</font> commence en premier")
        }
    }
}
