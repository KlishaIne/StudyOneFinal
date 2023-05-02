package com.example.podejscie69

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ReminderStorage(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("reminders_data", Context.MODE_PRIVATE)

    private val gson = Gson()
    companion object {
        private const val REMINDERS_KEY = "reminders"
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





    fun loadReminders(): MutableList<Reminder> {
        val jsonString = sharedPreferences.getString(REMINDERS_KEY, null)
        if (jsonString != null) {
            val type = object : TypeToken<MutableList<Reminder>>() {}.type
            return gson.fromJson(jsonString, type)
        }
        return mutableListOf()
    }

    fun deleteAllReminders() {
        sharedPreferences.edit().remove(REMINDERS_KEY).apply()
    }
}
