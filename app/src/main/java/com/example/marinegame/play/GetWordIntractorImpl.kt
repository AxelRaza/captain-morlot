package com.example.marinegame.play

import com.example.marinegame.model.Word
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetWordIntractorImpl : GameContract.GetWordIntractor {

    override fun getWordList(onFinishedListener: GameContract.GetWordIntractor.OnFinishedListener) {

        val generateWordApi = ApiClient.getClient().create(GenerateWordApi::class.java)
        val call : Call<List<Word>> =  generateWordApi.getWords()

        call.enqueue(object : Callback<List<Word>> {
            override fun onResponse(call: Call<List<Word>>, response: Response<List<Word>>) {
                onFinishedListener.onFinished(response.body()!!)
            }

            override fun onFailure(call: Call<List<Word>>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }


}