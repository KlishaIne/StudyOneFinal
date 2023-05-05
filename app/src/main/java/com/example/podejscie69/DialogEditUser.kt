package com.example.podejscie69

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.example.podejscie69.databinding.DialogEditUserBinding

class DialogEditUser(context: Context, private val user: User, private val onUserUpdated: (User) -> Unit) : Dialog(context) {

    private lateinit var binding: DialogEditUserBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the DatabaseHelper
        databaseHelper = DatabaseHelper(context)

        // Set the EditText fields with the current user information
        binding.etName.setText(user.name)
        binding.etEmail.setText(user.email)
        binding.etPassword.setText(user.password)

        // Set the click listener for the Save button
        binding.btnSave.setOnClickListener {
            val updatedName = binding.etName.text.toString()
            val updatedEmail = binding.etEmail.text.toString()
            val updatedPassword = binding.etPassword.text.toString()

            if (updatedName.isNotBlank() && updatedEmail.isNotBlank() && updatedPassword.isNotBlank()) {
                val updatedUser = User(user.id, updatedName, updatedEmail, updatedPassword)
                databaseHelper.updateUser(updatedUser)

                // Invoke the onUserUpdated callback function
                onUserUpdated(updatedUser)

                dismiss()
            } else {
                Toast.makeText(context, "All fields must be filled out", Toast.LENGTH_SHORT).show()
            }
        }

        // Set the click listener for the Cancel button
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}
