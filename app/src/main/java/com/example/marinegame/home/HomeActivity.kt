package com.example.marinegame.home

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.marinegame.R
import com.example.marinegame.RulesActivity

class HomeActivity : AppCompatActivity(), HomeContract.MvpView {

    var mPresenter : HomePresenter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mPresenter = HomePresenter(this)

    }

    fun play(view : View) {

    }

    fun openRules(view : View) {
        startActivity(Intent(this, RulesActivity::class.java))
    }
}
