package com.example.podejscie69

import android.icu.text.SimpleDateFormat
import java.util.*

data class Reminder(
    val id: String,
    val title: String,
    val dateTime: Date,
    val notificationId: Int,
    var isEnabled: Boolean = true
) {
    val dateTimeFormatted: String
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            return dateFormat.format(dateTime)
        }
}


