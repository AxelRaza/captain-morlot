package com.example.marinegame.model

import android.content.Context

object SharedPreferenciesManager {

    fun isFirstRunApp(context: Context) : Boolean {
        return context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            .getBoolean("isFirstRun", true)
    }

    fun setFirstRunAppToFalse(context: Context) {
        context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
            .putBoolean("isFirstRun", false).commit()
    }

}