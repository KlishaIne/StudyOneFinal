package com.example.podejscie69

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView

class TimerActivity : BaseActivity() {

    // Timer and MediaPlayer objects
    private var timer: CountDownTimer? = null
    private lateinit var mediaPlayer: MediaPlayer

    // TextView and Button for timer UI
    private lateinit var timerText: TextView
    private lateinit var startButton: Button

    // Time values for timer
    private var endTime: Long = 0
    private val millisInFuture = 1 * 60 * 1000L

    override fun getContentViewId(): Int {
        return R.layout.activity_timer
    }

    override fun getNavigationMenuItemId(): Int {
        return R.id.assignments
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        // Initialize MediaPlayer and UI elements
        mediaPlayer = MediaPlayer.create(this, R.raw.timer_end)
        timerText = findViewById(R.id.timer_text)
        startButton = findViewById(R.id.start_button)

        // Start timer when button is clicked
        startButton.setOnClickListener {
            startTimer()
        }
    }

    // Function to start timer
    private fun startTimer() {
        val countDownInterval = 1000L
        endTime = SystemClock.elapsedRealtime() + millisInFuture

        // Cancel any existing timer and start new one
        timer?.cancel()
        timer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
            }

            // Play sound and reset timer text when timer is finished
            override fun onFinish() {
                mediaPlayer.start()
                timerText.text = "1:00"
            }
        }.start()
    }

    // Function to update timer text on UI
    private fun updateTimerText(millisUntilFinished: Long) {
        val minutes = millisUntilFinished / 1000 / 60
        val seconds = millisUntilFinished / 1000 % 60
        timerText.text = String.format("%02d:%02d", minutes, seconds)
    }

    override fun onStop() {
        super.onStop()

        // Cancel timer and save end time in shared preferences
        timer?.cancel()
        val sharedPref = getSharedPreferences("PomodoroTimer", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putLong("endTime", endTime)
            apply()
        }
    }

    override fun onResume() {
        super.onResume()

        // Retrieve end time from shared preferences and start timer if necessary
        val sharedPref = getSharedPreferences("PomodoroTimer", MODE_PRIVATE)
        endTime = sharedPref.getLong("endTime", 0)
        if (endTime != 0L) {
            val remainingTime = endTime - SystemClock.elapsedRealtime()
            if (remainingTime > 0) {
                timer = object : CountDownTimer(remainingTime, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        updateTimerText(millisUntilFinished)
                    }

                    override fun onFinish() {
                        mediaPlayer.start()
                        timerText.text = "1:00"
                    }
                }.start()
            } else {
                timerText.text = "1:00"
            }
        }
    }

    override fun onDestroy() {
        // Release resources
        timer?.cancel()
        mediaPlayer.release()
        super.onDestroy()
    }
}
