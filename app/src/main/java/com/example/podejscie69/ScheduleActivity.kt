package com.example.podejscie69
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class ScheduleActivity : BaseActivity() {

    private lateinit var datePicker: DatePicker
    private lateinit var button: Button
    private lateinit var addEventButton: ImageButton
    private lateinit var eventDescription: EditText
    private lateinit var textView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        datePicker = findViewById(R.id.datePicker1)
        button = findViewById(R.id.button1)
        addEventButton = findViewById(R.id.addEventButton)
        eventDescription = findViewById(R.id.eventDescription)
        textView = findViewById(R.id.textView1)

        sharedPreferences = getSharedPreferences("events", Context.MODE_PRIVATE)

        button.setOnClickListener {
            val date = "${datePicker.dayOfMonth}-${datePicker.month + 1}-${datePicker.year}"
            textView.text = sharedPreferences.getString(date, "No events")
        }

        addEventButton.setOnClickListener {
            val date = "${datePicker.dayOfMonth}-${datePicker.month + 1}-${datePicker.year}"
            val eventText = eventDescription.text.toString()

            if (eventText.isNotBlank()) {
                val editor = sharedPreferences.edit()
                editor.putString(date, eventText)
                editor.apply()

                textView.text = sharedPreferences.getString(date, "No events")
                eventDescription.setText("")
            }
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_schedule
    }

    override fun getNavigationMenuItemId(): Int {
        return R.id.schedule
    }
}

