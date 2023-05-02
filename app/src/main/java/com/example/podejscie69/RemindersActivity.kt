package com.example.podejscie69

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RemindersActivity : BaseActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var reminderStorage: ReminderStorage
    private lateinit var addnew: FloatingActionButton
    override fun getContentViewId(): Int {
        return R.layout.activity_reminders
    }

    override fun getNavigationMenuItemId(): Int {
        return R.id.reminders
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        reminderStorage = ReminderStorage(this)
        addnew = findViewById(R.id.fab_add_reminder)
        addnew.setOnClickListener {
            // Open the RegisterActivity when the Register button is clicked
            val intent = Intent(this, AddReminderActivity::class.java)
            startActivity(intent)
        }
        loadReminders()

    }

    private fun loadReminders() {
        val reminders = reminderStorage.loadReminders()
        val adapter = ReminderAdapter()
        adapter.submitList(reminders)
        recyclerView.adapter = adapter
    }
}
