package com.example.marinegame.adapter

import android.widget.RelativeLayout
import android.support.annotation.NonNull
import android.view.ViewGroup
import android.content.Context
import android.widget.TextView
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.example.marinegame.R


class RulesSliderAdapter(val context : Context) : PagerAdapter() {

    private var layoutInflater: LayoutInflater? = null

    var images = intArrayOf(R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background)

    var titres =
        arrayOf("Présentation", "Règles du jeu", "Le Matelot", "Le Pirate", "Le Moussaillon", "Prêt pour jouer")

    var descriptions = arrayOf(
        "Bienvenue dans Captain Morgan, un bon jeu pour tuer le temps avec vos amis si vous en avez. De toute manière ici il n'y a pas d'amitiés qui tiennent, le but étant d'être le dernier survivant.",
        "Un mot va être donné au départ. la première personne devra dire un mot en relation avec le mot donné (suite, contraire, homophones, description, selon le rôle). La personne suivante devra donner un mot en relation avec celui de la personne précédente et ainsi de suite",
        "Tous les Matelots doivent dire un mot qui décrit le mot donné. \n\nPar exemple, si le mot est guitare, alors il peut dire : corde, instrument, acoustique ou encore manche",
        "Le Pirate doit dire un mot qui est soit une suite, soit le contraire ou qui va de pair avec le mot donné. \n\nPar exemple, si le mot est guitare, alors il peut dire acoustique ou électrique. \n\nSi le mot est Youtube, alors il peut dire Dailymotion ou heure (pour former youtuber).",
        "Le Moussaillon peut décrire, dire une suite, dire le contraire ou dire un mot qui va de pair avec celui donné. \n\nDe plus, lorsqu'il dira son mot, il devra désigner quelqu'un du doigt et celui-ci devra mimer son mot",
        "Lorsque ce sera son tour, le joueur devra dire ou mimer son mot dans les 3 secondes, sinon il mourra.\n\nSi le Moussaillon oublie de désigner quelqu'un du doigt, il mourra également.\n\n A chaque mort, le moussailon change et le pirate ne change que si ce dernier meurt."
    )

    override fun getCount(): Int {
        return titres.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.slide_rules, container, false)

        val image = view.findViewById<ImageView>(R.id.logo_place)
        val titre = view.findViewById<TextView>(R.id.titre_infoRecherche)
        val desc = view.findViewById<TextView>(R.id.description_info)

        image.setImageResource(images[position])
        titre.setText(titres[position])
        desc.setText(descriptions[position])

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

}