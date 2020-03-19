package com.example.marinegame.play

import android.widget.TextView
import com.example.marinegame.model.Word

interface GameContract {

    interface MvpView {
        fun onResponseFailure(throwable: Throwable)
        fun generateWord(word : String)
    }

    interface Presenter {
        fun onRefreshClick()

        fun requestDataFromServer()
    }

    interface GetWordIntractor {

        interface OnFinishedListener {
            fun onFinished(wordList: List<Word>)
            fun onFailure(t: Throwable)
        }

        fun getWordList(onFinishedListener: OnFinishedListener)
    }

}