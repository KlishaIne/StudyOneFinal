package com.example.podejscie69

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

// An abstract base activity that implements common functionality for activities with a bottom navigation bar
abstract class BaseActivity : AppCompatActivity() {

    // Abstract methods to be implemented by child activities
    abstract fun getContentViewId(): Int
    abstract fun getNavigationMenuItemId(): Int

    private lateinit var activityContent: FrameLayout
    protected lateinit var navigationView: BottomNavigationView

    // Sets up the logout button
    private fun setupLogoutButton() {
        val logoutButton = findViewById<MaterialButton>(R.id.logout_button)
        logoutButton.setOnClickListener {
            // Logout and navigate to main activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity() // This will close all activities in the back stack
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)

        // Get the selected menu item ID passed from the child activity
        val selectedItemId = intent.getIntExtra("selectedItemId", R.id.dashboard)

        // Set the selected menu item in the bottom navigation bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        bottomNavigationView.selectedItemId = selectedItemId

        // Set up the bottom navigation bar and logout button
        setupBottomNavigationMenu(bottomNavigationView)
        setupLogoutButton()

        activityContent = findViewById(R.id.activity_content)
        navigationView = findViewById(R.id.navigation_view)
    }

    // Override the setContentView method to set the content layout within the activityContent FrameLayout
    override fun setContentView(layoutResID: Int) {
        val inflater = LayoutInflater.from(this)
        inflater.inflate(layoutResID, activityContent, true)
    }

    // Sets up the bottom navigation menu with item selection handling
    private fun setupBottomNavigationMenu(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.putExtra("selectedItemId", R.id.dashboard)
                    startActivity(intent)
                    true
                }
                R.id.assignments -> {
                    val intent = Intent(this, TimerActivity::class.java)
                    intent.putExtra("selectedItemId", R.id.assignments)
                    startActivity(intent)
                    true
                }
                R.id.reminders -> {
                    val intent = Intent(this, RemindersActivity::class.java)
                    intent.putExtra("selectedItemId", R.id.reminders)
                    startActivity(intent)
                    true
                }
                R.id.schedule -> {
                    val intent = Intent(this, ScheduleActivity::class.java)
                    intent.putExtra("selectedItemId", R.id.schedule)
                    startActivity(intent)
                    true
                }
                R.id.tasks -> {
                    val intent = Intent(this, TasksActivity::class.java)
                    intent.putExtra("selectedItemId", R.id.tasks)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
