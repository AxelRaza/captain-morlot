package com.example.marinegame.play

import com.example.marinegame.model.Word
import retrofit2.Call
import retrofit2.http.GET

interface GenerateWordApi {

    @GET("frenchwords")
    fun getWords() : Call<List<Word>>
}