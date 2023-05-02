package com.example.podejscie69

import android.content.Intent
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

    private fun loginUser(email: String, password: String) {
        val user = databaseHelper.getUser(email)
        if (user != null && user.password == password) {
            Toast.makeText(this, "Logged in as $email", Toast.LENGTH_SHORT).show()

            // Navigate to the DashboardActivity
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    }
}
