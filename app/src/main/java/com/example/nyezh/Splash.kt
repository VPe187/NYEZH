package com.example.nyezh

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.os.postDelayed
import org.w3c.dom.Text
import java.util.concurrent.Executor
import kotlin.system.exitProcess

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val tvVPe: TextView = findViewById(R.id.textViewVPe)
        val tvTitle: TextView = findViewById(R.id.textViewTitle)
        val rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate)
        val fromRight = AnimationUtils.loadAnimation(this, R.anim.from_right)
        val fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom)
        val mainIntent = Intent(this@Splash, MainActivity::class.java)
        tvVPe.startAnimation(fromRight)
        tvTitle.startAnimation(fromBottom)
        Handler().postDelayed({
            tvVPe.startAnimation(rotateAnim)
        }, 1000)
        Handler().postDelayed({
            startActivity(mainIntent)
            exitProcess(0)
        },3000)
    }
}