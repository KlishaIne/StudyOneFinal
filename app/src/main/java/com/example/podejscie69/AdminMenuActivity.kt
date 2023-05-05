package com.example.podejscie69

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.podejscie69.databinding.AdminMenuActivityBinding
import com.example.podejscie69.databinding.DialogAddUserBinding

class AdminMenuActivity : AppCompatActivity(), UserAdapter.UserItemClickListener {

    // Declare variables for the activity
    private lateinit var binding: AdminMenuActivityBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var selectedUser: User
    private lateinit var userList: MutableList<User>
    private lateinit var userAdapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminMenuActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database helper and set up the RecyclerView
        databaseHelper = DatabaseHelper(this)
        setupRecyclerView()

        // Set up the "Add User" button to display a dialog for adding a new user
        binding.btnAddUser.setOnClickListener {
            // Inflate the dialog_add_user layout and create a binding
            val inflater = LayoutInflater.from(this)
            val binding = DialogAddUserBinding.inflate(inflater)

            // Create and show the dialog
            AlertDialog.Builder(this)
                .setTitle("Add User")
                .setView(binding.root)
                .setPositiveButton("Add") { dialog, _ ->
                    // Get the user input and create a new User object
                    val name = binding.etName.text.toString()
                    val email = binding.etEmail.text.toString()
                    val password = binding.etPassword.text.toString()
                    val user = User(0, name, email, password)

                    // Add the user to the database and show a success or failure message
                    val isUserAdded = databaseHelper.addUser(user)
                    if (isUserAdded) {
                        Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
                        setupRecyclerView()
                    } else {
                        Toast.makeText(
                            this,
                            "Failed to add user(user might already exist)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }

        // Set up the "Delete User" button to delete the selected user from the database
        binding.btnDeleteUser.setOnClickListener {
            selectedUser?.let { user ->
                if (databaseHelper.deleteUser(user.id)) {
                    Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show()
                    setupRecyclerView()
                } else {
                    Toast.makeText(this, "Failed to delete user", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Set up the "Edit User" button to display a dialog for editing the selected user
        binding.btnEditUser.setOnClickListener {
            if (::selectedUser.isInitialized) {
                val dialogEditUser = DialogEditUser(this, selectedUser) { updatedUser ->
                    // Update the user list in the adapter and refresh the RecyclerView
                    val updatedUserList = userList.map {
                        if (it.id == selectedUser.id) updatedUser else it
                    }.toMutableList()
                    userAdapter.updateUserList(updatedUserList)
                }
                dialogEditUser.show()
            } else {
                Toast.makeText(this, "Please select a user to edit", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up the "View Activity Logs" button to start the ActivityLogsActivity
        binding.btnViewActivityLogs.setOnClickListener {
            val intent = Intent(this, ActivityLogsActivity::class.java)
            startActivity(intent)
        }

        // Set up the "Logout" button to return to the MainActivity
        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Set up the RecyclerView with the list of users from the database
    private fun setupRecyclerView() {
        userList = databaseHelper.getAllUsers().toMutableList()
        userAdapter = UserAdapter(userList, this)
        binding.rvUserList.adapter = userAdapter
        binding.rvUserList.layoutManager = LinearLayoutManager(this)
        binding.rvUserList.setHasFixedSize(true)
    }

    // Handle clicks on a user item in the RecyclerView
    override fun onUserItemClick(user: User) {
        selectedUser = user
    }
}