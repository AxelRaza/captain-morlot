package com.example.marinegame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class RoleDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_detail)

        val roleSelected = intent.extras["Role"];

        val role = findViewById<TextView>(R.id.role_detail_title_textview)
        val description = findViewById<TextView>(R.id.role_detail_desc_textview)

        when(roleSelected) {
            "Matelot" -> {
                role.text = roleSelected.toString()
                description.text = "Tous les matelots doivent dire un mot qui décrit le mot donné \n" +
                    "Exemple : guitare -> corde/instrument/acoustique/manche"
            }
            "Pirate" -> {
                role.text = roleSelected.toString()
                description.text = "Le pirate doit dire un mot qui est soit une suite, soit le contraire du mot donné.\n" +
                    "Exemple : guitare -> acoustique/électrique\n" +
                    "\t\t  youtube -> dailymotion\n" +
                    "\t\t  élection -> maire -> père\n" +
                    "\t\t  chat -> botté\n" +
                    "\t\t  youtube -> heure (youtubeur) / rouge -> âtre"
            }
            "Moussaillon" -> {
                role.text = roleSelected.toString()
                description.text = "Le moussaillon peut décrire, dire la suite ou le contraire du mot donné\n" +
                    "Lorsqu'il dira son mot, il devra désigner quelqu'un du doigt et celui-ci doit mimer son mot"
            }
        }

    }

    fun backToRules(view : View) {
        finish()
    }
}
