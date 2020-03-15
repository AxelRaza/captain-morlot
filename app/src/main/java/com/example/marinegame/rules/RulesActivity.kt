package com.example.marinegame

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.marinegame.rules.RulesContract
import com.example.marinegame.rules.RulesPresenter
import com.oc.rss.oc_rss.RulesAdapter
import java.util.*


class RulesActivity : AppCompatActivity(), RulesContract.MvpView, RulesAdapter.onRoleListener {

    var mPresenter : RulesContract.Presenter ?= null
    var roles : List<Pair<String, String>> = Arrays.asList(
    Pair("Matelot", "Le bon camarade qui obéit comme un chien"),
    Pair("Pirate", "Aussi méchant que le capitaine Crochet"),
    Pair("Moussaillon", "Le débutant de seconde zone qui va ruiner ta partie")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)
        mPresenter = RulesPresenter(this)
        initRecyclerView(roles)
    }

    fun backToHome(view : View) {
        finish()
    }

    private fun initRecyclerView(roles : List<Pair<String, String>>) {
        val rv = findViewById<View>(R.id.roles_list) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = RulesAdapter(roles, this)
    }

    override fun onRoleClick(position: Int) {
        val intent = Intent(this, RoleDetail::class.java)
        intent.putExtra("Role", roles[position].first)
        startActivity(intent)
    }
}
