package com.example.marinegame.play

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.marinegame.R
import com.example.marinegame.model.Game
import com.example.marinegame.model.Word
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameActivity : AppCompatActivity(), GameContract.MvpView {

    lateinit var randomWord : TextView
    lateinit var presenter : GameContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        randomWord = findViewById(R.id.random_word_textview)
        presenter = GamePresenter(this, GetWordIntractorImpl())
    }

    fun backToHome(view: View) {
        finish()
    }

    fun onClickScreen(view: View) {
        presenter.onRefreshClick()
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
