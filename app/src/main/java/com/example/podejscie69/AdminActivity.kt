package com.example.podejscie69

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.podejscie69.databinding.AdminActivityBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: AdminActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bLoginAdmin.setOnClickListener {
            val email = binding.etEmailAdmin.text.toString()
            val password = binding.etPasswordAdmin.text.toString()

            // Replace with your admin email and password.
            if (email == "admin" && password == "admin") {
                val intent = Intent(this, AdminMenuActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
        binding.bBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
