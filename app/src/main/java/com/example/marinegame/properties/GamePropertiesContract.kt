package com.example.marinegame.properties

interface GamePropertiesContract {

    interface MvpView {
        fun showRoles(progressMax : Int, nbPlayers : Int)
        fun showRolesMinPlayer(progressMax : Int, nbPlayers : Int)
        fun hideRoles(nbPlayers: Int)
    }

    interface Presenter {
        fun updateRoles()
    }
}