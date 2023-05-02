package com.example.podejscie69


import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView


class AssignmentsActivity : BaseActivity() {

    private var timer: CountDownTimer? = null
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var timerText: TextView
    private lateinit var startButton: Button
    private var endTime: Long = 0
    private val millisInFuture = 1 * 60 * 1000L


    override fun getContentViewId(): Int {
        return R.layout.activity_assignments
    }

    override fun getNavigationMenuItemId(): Int {
        return R.id.assignments
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignments)

        mediaPlayer = MediaPlayer.create(this, R.raw.timer_end)
        timerText = findViewById(R.id.timer_text)
        startButton = findViewById(R.id.start_button)

        startButton.setOnClickListener {
            startTimer()
        }
    }

    private fun startTimer() {
        val countDownInterval = 1000L
        endTime = SystemClock.elapsedRealtime() + millisInFuture

        timer?.cancel()
        timer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
            }

            override fun onFinish() {
                mediaPlayer.start()
                timerText.text = "1:00"
            }
        }.start()
    }
    private fun updateTimerText(millisUntilFinished: Long) {
        val minutes = millisUntilFinished / 1000 / 60
        val seconds = millisUntilFinished / 1000 % 60
        timerText.text = String.format("%02d:%02d", minutes, seconds)
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()

        val sharedPref = getSharedPreferences("PomodoroTimer", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putLong("endTime", endTime)
            apply()
        }
    }

    override fun onResume() {
        super.onResume()

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
        timer?.cancel()
        mediaPlayer.release()
        super.onDestroy()
    }
}
