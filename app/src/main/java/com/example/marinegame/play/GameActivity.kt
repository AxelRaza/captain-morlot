package com.example.marinegame.play

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.marinegame.R
import com.example.marinegame.model.Game
import com.example.marinegame.model.Player
import com.example.marinegame.model.Word
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class GameActivity : AppCompatActivity(), GameContract.MvpView {

    lateinit var randomWord : TextView
    lateinit var presenter : GameContract.Presenter
    lateinit var roleDialog : Dialog
    var count : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        randomWord = findViewById(R.id.random_word_textview)
        presenter = GamePresenter(this, GetWordIntractorImpl())
        showRoleDialog()
    }

    fun backToHome(view: View) {
        finish()
    }

    fun onClickScreen(view: View) {
        count++
        if(count%5 == 0)
            showRoleDialog()
        presenter.onRefreshClick()
    }

    fun showRoleDialog() {
        roleDialog = Dialog(this)
        roleDialog.setContentView(R.layout.roles_popup)

        val okButton = roleDialog.findViewById<Button>(R.id.ok_button)
        val pirate = roleDialog.findViewById<TextView>(R.id.player_pirate_name_textview)
        val moussaillon = roleDialog.findViewById<TextView>(R.id.player_mous_name_textview)
        val playersList = intent.extras["playersList"] as ArrayList<Player>

        while (pirate.text.equals(moussaillon.text)) {
            pirate.text = playersList[Random.nextInt(0, playersList.size)].name
            moussaillon.text = playersList[Random.nextInt(0, playersList.size)].name
        }

        okButton.setOnClickListener {
            roleDialog.dismiss()
        }
        roleDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        roleDialog.show()
    }

    override fun onResponseFailure(throwable: Throwable) {
        Toast.makeText(this,
            "Oups, une erreur est survenue commandant",
            Toast.LENGTH_LONG).show();
    }

    override fun generateWord(word: String) {
        randomWord.text = word
    }
}
