package com.example.marinegame.play

import android.widget.TextView
import com.example.marinegame.model.Player
import com.example.marinegame.model.Word

interface GameContract {

    interface MvpView {
        fun onResponseFailure(throwable: Throwable)
        fun generateWord(word : String)
        fun updateRoles()
        fun updateRolesPopup(player1 : Player, player2: Player)
        fun updateFirstPlayer(player: Player)
    }

    interface Presenter {
        fun onRefreshClick()
        fun requestDataFromServer()
        fun rolesInit()
        fun rolesAssign()
        fun getRandomPlayer()
    }

    interface GetWordIntractor {

        interface OnFinishedListener {
            fun onFinished(wordList: List<Word>)
            fun onFailure(t: Throwable)
        }

        fun getWordList(onFinishedListener: OnFinishedListener)
    }

}