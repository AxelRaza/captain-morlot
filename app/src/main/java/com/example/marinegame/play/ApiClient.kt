package com.example.marinegame.play

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        fun getClient() : Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://bridge.buddyweb.fr/api/wordgeneration/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

}