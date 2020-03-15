package com.example.marinegame.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.marinegame.R
import com.example.marinegame.RulesActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val playButton = findViewById<Button>(R.id.play_button)
        playButton.setOnClickListener {

        }

    }

    fun openRules(view : View) {
        startActivity(Intent(this, RulesActivity::class.java))
    }
}
