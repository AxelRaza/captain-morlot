package com.example.marinegame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.marinegame.rules.RulesContract
import com.example.marinegame.rules.RulesPresenter
import android.support.v4.view.ViewPager
import com.example.marinegame.adapter.RulesSliderAdapter
import android.text.Html
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView




class RulesActivity : AppCompatActivity(), RulesContract.MvpView {

    lateinit var mPresenter : RulesContract.Presenter
    lateinit var pointsLayout : LinearLayout
    lateinit var points : Array<TextView?>
    lateinit var btnPrec : Button
    lateinit var btnSuiv : Button
    var currentPage : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)
        mPresenter = RulesPresenter(this)

        val sliderInformationAdapter = RulesSliderAdapter(this)
        val slideInformation = findViewById<ViewPager>(R.id.information_viewpager)
        slideInformation.setAdapter(sliderInformationAdapter)

        pointsLayout = findViewById(R.id.pointsLayout)
        ajouterPoints(0)
        slideInformation.addOnPageChangeListener(viewListener)

        btnSuiv = findViewById(R.id.button_suivant)
        btnSuiv.setOnClickListener {
            if (currentPage === points.size - 1)
                finish()
            else
                slideInformation.setCurrentItem(currentPage + 1)
        }

        btnPrec = findViewById(R.id.button_precedent)
        btnPrec.setVisibility(View.INVISIBLE)
        btnPrec.setOnClickListener {
            slideInformation.setCurrentItem(currentPage - 1)
        }

    }

    fun backToHome(view : View) {
        finish()
    }

    fun ajouterPoints(position: Int) {
        points = arrayOfNulls(6)
        pointsLayout.removeAllViews()
        for (i in 0 until points.size) {
            points[i] = TextView(this)
            points[i]!!.text = (Html.fromHtml("&#8226;"))
            points[i]!!.textSize = 35F
            points[i]!!.setTextColor(resources.getColor(R.color.background_material_dark))
            pointsLayout.addView(points[i])
        }
        if (points.size > 0)
            points[position]!!.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    var viewListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(i: Int, v: Float, i1: Int) {

        }

        override fun onPageSelected(i: Int) {
            ajouterPoints(i)
            currentPage = i

            if (i == 0) {
                btnSuiv.setEnabled(true)
                btnPrec.setEnabled(false)
                btnPrec.setVisibility(View.INVISIBLE)

                btnSuiv.setText("Suiv")
                btnPrec.setText("")
            } else if (i == points.size - 1) {
                btnSuiv.setEnabled(true)
                btnPrec.setEnabled(true)
                btnPrec.setVisibility(View.VISIBLE)

                btnSuiv.setText("Terminer")
                btnPrec.setText("Prec")
            } else {
                btnSuiv.setEnabled(true)
                btnPrec.setEnabled(true)
                btnPrec.setVisibility(View.VISIBLE)

                btnSuiv.setText("Suiv")
                btnPrec.setText("Prec")
            }
        }

        override fun onPageScrollStateChanged(i: Int) {

        }
    }


}
