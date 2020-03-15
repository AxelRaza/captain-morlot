package com.example.marinegame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.marinegame.rules.RulesContract
import com.example.marinegame.rules.RulesPresenter
import com.oc.rss.oc_rss.RulesAdapter


class RulesActivity : AppCompatActivity(), RulesContract.MvpView {

    var mPresenter : RulesContract.Presenter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)
        mPresenter = RulesPresenter(this)

        val rv = findViewById<View>(R.id.roles_list) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = RulesAdapter()
    }

    fun backToHome(view : View) {
        finish()
    }
}
