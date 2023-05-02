package com.example.podejscie69
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.util.Log

class TaskStorage(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("tasks_data", Context.MODE_PRIVATE)

    private val gson = Gson()
    companion object {
        private const val TASKS_KEY = "tasks"
    }
    fun saveTasks(tasks: List<Task>) {
        val taskJson = gson.toJson(tasks)
        sharedPreferences.edit().putString(TASKS_KEY, taskJson).apply()
        Log.d("TaskStorage", "Tasks saved: $taskJson")
    }

    fun loadTasks(): MutableList<Task> {
        val jsonString = sharedPreferences.getString(TASKS_KEY, null)
        if (jsonString != null) {
            val type = object : TypeToken<MutableList<Task>>() {}.type
            return gson.fromJson(jsonString, type)
        }
        return mutableListOf()
    }
    fun deleteAllTasks() {
        sharedPreferences.edit().remove(TASKS_KEY).apply()
    }


}
