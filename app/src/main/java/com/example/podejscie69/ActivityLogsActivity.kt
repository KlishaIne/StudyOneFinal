package com.example.podejscie69

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.podejscie69.databinding.ActivityLogsBinding

class ActivityLogsActivity : AppCompatActivity() {
    // Declare the binding and database helper properties
    private lateinit var binding: ActivityLogsBinding
    private lateinit var databaseHelper: DatabaseHelper

    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database helper and setup the recycler view
        databaseHelper = DatabaseHelper(this)
        setupRecyclerView()
    }

    // Sets up the recycler view to display the activity logs
    private fun setupRecyclerView() {
        // Retrieve all activity logs from the database
        val activityLogs = databaseHelper.getAllActivityLogs()
        // Create an adapter for the activity logs and set it on the recycler view
        val adapter = ActivityLogsAdapter(activityLogs)
        binding.recyclerViewActivityLogs.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewActivityLogs.adapter = adapter
    }
}
