package com.example.podejscie69
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.podejscie69.R
import com.google.android.material.button.MaterialButton


abstract class BaseActivity : AppCompatActivity() {
    abstract fun getContentViewId(): Int
    abstract fun getNavigationMenuItemId(): Int
    private lateinit var activityContent: FrameLayout
    protected lateinit var navigationView: BottomNavigationView
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
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        val selectedItemId = intent.getIntExtra("selectedItemId", R.id.dashboard)
        bottomNavigationView.selectedItemId = selectedItemId

        setupBottomNavigationMenu(bottomNavigationView)
        setupLogoutButton()
        activityContent = findViewById(R.id.activity_content)
        navigationView = findViewById(R.id.navigation_view)
    }

    override fun setContentView(layoutResID: Int) {
        val inflater = LayoutInflater.from(this)
        inflater.inflate(layoutResID, activityContent, true)
    }

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
                    val intent = Intent(this, AssignmentsActivity::class.java)
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
