package com.example.marinegame.loading

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.marinegame.R
import com.example.marinegame.home.HomeActivity

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        Handler().postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_top)
            finish()
        }, 3000)
    }
}
