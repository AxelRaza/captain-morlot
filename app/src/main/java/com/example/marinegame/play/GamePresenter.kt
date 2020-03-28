package com.example.marinegame.play

import com.example.marinegame.model.Game
import com.example.marinegame.model.Word
import kotlin.random.Random

class GamePresenter(val mView : GameContract.MvpView, val game : Game, val getWordIntractor: GameContract.GetWordIntractor) : GameContract.Presenter, GameContract.GetWordIntractor.OnFinishedListener {

    override fun getRandomPlayer() {
        val player = game.getRandomPlayer()
        mView.updateFirstPlayer(player)
    }

    override fun rolesInit() {
        game.rolesInit()
    }

    override fun rolesAssign() {
        game.rolesAssign()
        mView.updateRoles()
        if(!game.moussaillonExist() && !game.pirateExist())
            mView.updateRolesPopup(game.playersList()[0], game.playersList()[1])
    }

    override fun onRefreshClick() {
        getWordIntractor.getWordList(this)
    }

    override fun requestDataFromServer() {
        getWordIntractor.getWordList(this)
    }

    override fun onFinished(wordList: List<Word>) {
        mView.generateWord(wordList[Random.nextInt(0 , wordList.size)].name)
    }

    override fun onFailure(t: Throwable) {
        mView.onResponseFailure(t)
    }

}