package com.example.podejscie69

import android.app.AlarmManager
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.podejscie69.ReminderReceiver.Companion.EXTRA_REMINDER_TITLE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.util.Log


class ReminderStorage(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("reminders_data", Context.MODE_PRIVATE)

    private val gson = Gson()
    companion object {
        private const val REMINDERS_KEY = "reminders"
        private const val ACTION_REMINDER = "com.example.podejscie69.ACTION_REMINDER"
    }

    fun saveReminder(reminder: Reminder) {
        val reminders = loadReminders()
        reminders.add(reminder)
        saveReminders(reminders)
    }

    private fun saveReminders(reminders: List<Reminder>) {
        val reminderJson = gson.toJson(reminders)
        sharedPreferences.edit().putString(REMINDERS_KEY, reminderJson).apply()
    }

    fun scheduleReminder(context: Context, reminder: Reminder) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = ACTION_REMINDER
            putExtra(EXTRA_NOTIFICATION_ID, reminder.notificationId)
            putExtra(EXTRA_REMINDER_TITLE, reminder.title)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminder.dateTime.time, pendingIntent)
        Log.d("ReminderStorage", "Alarm scheduled for ${reminder.dateTimeFormatted} with notificationId: ${reminder.notificationId}")

    }


    fun cancelReminder(context: Context, reminder: Reminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.notificationId,
            Intent(context, ReminderReceiver::class.java).apply {
                action = ACTION_REMINDER
                putExtra(ReminderReceiver.EXTRA_REMINDER_ID, reminder.notificationId)
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
    }




    fun loadReminders(): MutableList<Reminder> {
        val jsonString = sharedPreferences.getString(REMINDERS_KEY, null)
        if (jsonString != null) {
            val type = object : TypeToken<MutableList<Reminder>>() {}.type
            return gson.fromJson(jsonString, type)
        }
        return mutableListOf()
    }
    fun deleteReminder(id: String) {
        val reminders = loadReminders()
        val reminderToRemove = reminders.find { it.id == id }
        if (reminderToRemove != null) {
            reminders.remove(reminderToRemove)
            saveReminders(reminders)
        }
    }

}
