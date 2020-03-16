package com.example.marinegame.play

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.marinegame.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameActivity : AppCompatActivity() {

    lateinit var words : List<Word>
    lateinit var randomWord : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        randomWord = findViewById<TextView>(R.id.random_word_textview)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://bridge.buddyweb.fr/api/wordgeneration/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val generateWordApi = retrofit.create(GenerateWordApi::class.java)
        val call : Call<List<Word>> =  generateWordApi.getWords()

        call.enqueue(object : Callback<List<Word>> {
            override fun onResponse(call: Call<List<Word>>, response: Response<List<Word>>) {

                if (!response.isSuccessful()) {
                    randomWord.text = "Aucun mot disponible. Vérifiez votre connexion"
                    return
                }

                words = response.body()!!

            }

            override fun onFailure(call: Call<List<Word>>, t: Throwable) {
                randomWord.text = t.message
            }
        })

    }

    fun backToHome(view: View) {
        finish()
    }

    fun generateWord(view: View) {
        randomWord.text = Game.getRandomWord(words)
    }
}
