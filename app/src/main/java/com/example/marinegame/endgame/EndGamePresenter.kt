package com.example.marinegame.endgame

import com.example.marinegame.model.Game
import com.example.marinegame.model.Player

class EndGamePresenter(val mView : EndGameContract.MvpView, val game : Game) : EndGameContract.Presenter {

    override fun updateGame(player: Player) {
        if(game.playersList().size > Game.NB_MIN_PLAYERS) {
            game.removePlayer(player)
            mView.updateGameView()
        }
        else if(game.playersList().size == Game.NB_MIN_PLAYERS) {
            game.removePlayer(player)
            mView.updateEndGame()
        }
    }

    override fun showPlayersList() {
        var position = 0
        for(player in game.playersList()) {
            mView.updateDesignPlayer(player, position++)
        }
    }

}