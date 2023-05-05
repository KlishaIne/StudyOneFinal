package com.example.podejscie69

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.podejscie69.databinding.AdminActivityBinding


class AdminActivity : AppCompatActivity() {
    // Declare the binding property for this activity
    private lateinit var binding: AdminActivityBinding

    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Login as admin when the login button is clicked
        binding.bLoginAdmin.setOnClickListener {
            // Retrieve the email and password entered by the user
            val email = binding.etEmailAdmin.text.toString()
            val password = binding.etPasswordAdmin.text.toString()

            // Replace with your admin email and password.
            // Check if the user entered the correct email and password
            if (email == "admin" && password == "admin") {
                // Start the admin menu activity
                val intent = Intent(this, AdminMenuActivity::class.java)
                startActivity(intent)
            } else {
                // Show an error message if the user entered an incorrect email or password
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        // Go back to the main activity when the back button is clicked
        binding.bBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
