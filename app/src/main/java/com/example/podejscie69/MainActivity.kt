package com.example.podejscie69

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var bLogin: Button
    private lateinit var bRegister: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var bAdmin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()
        databaseHelper = DatabaseHelper(this)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        bLogin = findViewById(R.id.bLogin)
        bRegister = findViewById(R.id.bRegister)
        bAdmin = findViewById(R.id.bAdmin)

        bLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (validateInput(email, password)) {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter a valid email and password.", Toast.LENGTH_SHORT).show()
            }
        }

        bRegister.setOnClickListener {
            // Open the RegisterActivity when the Register button is clicked
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        bAdmin.setOnClickListener {
            // Open the RegisterActivity when the Register button is clicked
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }
    }


    private fun validateInput(email: String, password: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotEmpty()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Reminders"
            val descriptionText = "Channel for reminders"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(ReminderReceiver.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

