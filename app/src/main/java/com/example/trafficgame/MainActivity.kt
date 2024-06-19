package com.example.trafficgame

import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.color.utilities.Score

class MainActivity : AppCompatActivity(),GameTask {
    lateinit var rootLayout :ConstraintLayout
    lateinit var startBtn :Button
    lateinit var mGameView: GameView
    lateinit var score: TextView
    lateinit var highScoreText: TextView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highScoreText = findViewById(R.id.highscore)
        mGameView = GameView(this,this)

        val highScore = sharedPreferences.getInt("highScore", 0)
        highScoreText.text = "High Score : $highScore"

        startBtn.setOnClickListener {

            startGame()

        }
    }

    private fun startGame() {
        mGameView = GameView(this, this)
        mGameView.setBackgroundResource(R.drawable.road)
        rootLayout.addView(mGameView)
        startBtn.visibility = View.GONE
    }

    override fun closeGame(mScore: Int) {
        score.text = "Score : $mScore"
        val highScore = sharedPreferences.getInt("highScore", 0)
        if (mScore > highScore) {
            with(sharedPreferences.edit()) {
                putInt("highScore", mScore)
                apply()
            }
            highScoreText.text = "High Score : $mScore"
        }
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
    }
}
