package com.example.podejscie69

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast



class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextName = findViewById(R.id.textInputEditText)
        editTextEmail = findViewById(R.id.editTextTextEmailAddress2)
        editTextPassword = findViewById(R.id.editTextTextPassword2)
        editTextConfirmPassword = findViewById(R.id.editTextTextPassword3)
        buttonRegister = findViewById(R.id.bRegister)

        databaseHelper = DatabaseHelper(this)

        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(0, name, email, password)
                val success = databaseHelper.addUser(user)

                if (success) {
                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error creating account, please try again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}