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

    private lateinit var binding: AdminMenuActivityBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var selectedUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminMenuActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        setupRecyclerView()

       binding.btnAddUser.setOnClickListener {
            // Inflate the dialog_add_user layout and create a binding
            val inflater = LayoutInflater.from(this)
            val binding = DialogAddUserBinding.inflate(inflater)

            // Create and show the dialog
            AlertDialog.Builder(this)
                .setTitle("Add User")
                .setView(binding.root)
                .setPositiveButton("Add") { dialog, _ ->
                    val name = binding.etName.text.toString()
                    val email = binding.etEmail.text.toString()
                    val password = binding.etPassword.text.toString()

                    val user = User(0, name, email, password)
                    val isUserAdded = databaseHelper.addUser(user)

                    if (isUserAdded) {
                        Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
                        setupRecyclerView()
                    } else {
                        Toast.makeText(this, "Failed to add user(user might already exist)", Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }

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


        binding.btnEditUser.setOnClickListener {
            // Code for editing user functionality
        }

        binding.btnViewActivityLogs.setOnClickListener {
            // Code for viewing activity logs functionality
        }

        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val userList = databaseHelper.getAllUsers()
        val userAdapter = UserAdapter(userList, this)
        binding.rvUserList.adapter = userAdapter
        binding.rvUserList.layoutManager = LinearLayoutManager(this)
        binding.rvUserList.setHasFixedSize(true)
    }

    override fun onUserItemClick(user: User) {
        selectedUser = user
    }
}
