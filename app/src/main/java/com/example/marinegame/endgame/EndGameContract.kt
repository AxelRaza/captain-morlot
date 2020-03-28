package com.example.marinegame.endgame

import com.example.marinegame.model.Player

interface EndGameContract {

    interface MvpView {
        fun updateDesignPlayer(player: Player, position : Int)
        fun updateGameView()
        fun updateEndGame()
    }

    interface Presenter {
        fun showPlayersList()
        fun updateGame(player: Player)
    }
}