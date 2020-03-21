package com.example.marinegame.model

import kotlin.random.Random


object Game {

    lateinit var players : ArrayList<Player>

    fun getRandomWord(words : List<Word>): String {
        return words[Random.nextInt(0 , words.size)].name
    }

}