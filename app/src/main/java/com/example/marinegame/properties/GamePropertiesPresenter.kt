package com.example.marinegame.properties

import com.example.marinegame.model.Game

class GamePropertiesPresenter(val mView : GamePropertiesContract.MvpView, val game : Game) : GamePropertiesContract.Presenter {

    override fun updateRoles() {
        val nbPlayers = game.playersList().size

        if(nbPlayers == 2)
            mView.hideRoles(nbPlayers)
        if(nbPlayers < 6 && nbPlayers > 2)
            mView.showRolesMinPlayer(100, nbPlayers)
        else if(nbPlayers < 10 && nbPlayers > 5)
            mView.showRoles(200, nbPlayers)
        else if(nbPlayers < 15 && nbPlayers > 9)
            mView.showRoles(300, nbPlayers)
        else if(nbPlayers < 19 && nbPlayers > 14)
            mView.showRoles(400, nbPlayers)
    }

}