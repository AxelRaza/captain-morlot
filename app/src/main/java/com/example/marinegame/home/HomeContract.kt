package com.example.marinegame.home

interface HomeContract {

    interface MvpView {
        fun updatePlayersList()
    }

    interface Presenter {
        fun addPlayer(name : String, role : String)
        fun removePlayerAt(position : Int)
    }

}