package com.example.marinegame.home

import com.example.marinegame.model.Game


class HomePresenter(val mView : HomeContract.MvpView, val game : Game) : HomeContract.Presenter {

    override fun addPlayer(name: String, role: String) {
        game.addPlayer(game.createPlayer(name, role))
        mView.updatePlayersList()
    }

    override fun removePlayerAt(position: Int) {
        game.removePlayerAt(position)
        mView.updatePlayersList()
    }


}