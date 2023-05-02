package com.example.podejscie69

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class AddReminderActivity : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var textViewDateTime: TextView
    private lateinit var buttonSetDateTime: Button
    private lateinit var buttonAddReminder: Button

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    private var selectedDateTime: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)

        editTextTitle = findViewById(R.id.reminder_title)
        textViewDateTime = findViewById(R.id.reminder_date_time)
        buttonSetDateTime = findViewById(R.id.button_set_date_time)
        buttonAddReminder = findViewById(R.id.button_add_reminder)

        updateDateTime()

        buttonSetDateTime.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    selectedDateTime.set(Calendar.YEAR, year)
                    selectedDateTime.set(Calendar.MONTH, month)
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val timePickerDialog = TimePickerDialog(
                        this,
                        { _, hourOfDay, minute ->
                            selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            selectedDateTime.set(Calendar.MINUTE, minute)
                            updateDateTime()
                        },
                        selectedDateTime.get(Calendar.HOUR_OF_DAY),
                        selectedDateTime.get(Calendar.MINUTE),
                        true
                    )
                    timePickerDialog.show()
                },
                selectedDateTime.get(Calendar.YEAR),
                selectedDateTime.get(Calendar.MONTH),
                selectedDateTime.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        buttonAddReminder.setOnClickListener {
            val reminderTitle = editTextTitle.text.toString().trim()
            if (reminderTitle.isNotEmpty()) {
                val intent = Intent(this, RemindersActivity::class.java)
                startActivity(intent)
                val newReminder = Reminder(UUID.randomUUID().toString(), reminderTitle, selectedDateTime.time, true)
                ReminderStorage(this).saveReminder(newReminder)
            }
        }
    }

    private fun updateDateTime() {
        textViewDateTime.text = dateFormat.format(selectedDateTime.time)
    }
}
