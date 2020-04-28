package com.example.countdownopenactivityexample

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val handler = Handler()

    var eventTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide the countdown
        countdown_text.visibility = View.GONE


        countdown_btn.setOnClickListener {

            // Show the countdown
            countdown_text.visibility = View.VISIBLE

            // Set Event Time
            eventTime = System.currentTimeMillis() + 3000

            // Update TextView every second
            handler.post(object : Runnable {
                override fun run() {
                    // Keep the postDelayed before the updateTime(), so when the event ends, the handler will stop too.
                    handler.postDelayed(this, 1000)
                    updateTime()
                }
            })
        }

    }

    fun updateTime() {
        // Set Current Time
        val currentTime = System.currentTimeMillis()

        // Find how many milliseconds until the event
        val diff = eventTime - currentTime

        // Change the milliseconds to seconds
        val seconds = (diff / 1000) % 60

        // Display Countdown
        // If you want to show 3, 2, 1 instead of 2, 1, 0  add '+ 1' in the seconds
        countdown_text.text = "${seconds + 1}s"

        // Show different text when the event has passed
        endEvent(currentTime, eventTime)
    }

    private fun endEvent(currenttime: Long, eventtime: Long) {
        if (currenttime >= eventtime) {
            //Stop Handler
            handler.removeMessages(0)

            // Hide the Countdown TextView again
            countdown_text.visibility = View.GONE

            // Open new Activity
            val intent = Intent(this, SecondActivity::class.java)
            this.startActivity(intent)
        }
    }
}
