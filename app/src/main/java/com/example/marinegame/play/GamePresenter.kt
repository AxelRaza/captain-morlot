package com.example.marinegame.play

import com.example.marinegame.model.Game
import com.example.marinegame.model.Word
import kotlin.random.Random

class GamePresenter(val mView : GameContract.MvpView, val getWordIntractor: GameContract.GetWordIntractor) : GameContract.Presenter, GameContract.GetWordIntractor.OnFinishedListener {

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