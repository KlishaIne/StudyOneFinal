package com.example.podejscie69

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class TasksActivity : BaseActivity() {

    var tasks = mutableListOf<Task>()
    val taskAdapter = TaskAdapter()
    private lateinit var taskStorage: TaskStorage
    private lateinit var editTextTask: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun getContentViewId(): Int {
        return R.layout.activity_tasks
    }

    override fun onResume() {
        super.onResume()
        tasks = taskStorage.loadTasks().toMutableList()
        taskAdapter.submitList(tasks)
    }

    override fun getNavigationMenuItemId(): Int {
        return R.id.tasks
    }

    private fun addTask(newTaskName: String) {
        val newTask = Task(UUID.randomUUID().toString(), newTaskName)
        tasks.add(newTask)
        taskAdapter.notifyItemInserted(tasks.size - 1)
        editTextTask.setText("")
        taskStorage.saveTasks(tasks)
    }

    private fun deleteTasks() {
        tasks.clear()
        taskAdapter.submitList(tasks.toList())
        taskStorage.saveTasks(tasks)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        editTextTask = findViewById(R.id.taskInput)

        taskStorage = TaskStorage(this)
        tasks = taskStorage.loadTasks()

        sharedPreferences = getSharedPreferences("tasks_preferences", Context.MODE_PRIVATE)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val editTextTask: EditText = findViewById(R.id.taskInput)
        val buttonAddTask: Button = findViewById(R.id.addButton)

        recyclerView.adapter = taskAdapter.apply { sharedPreferences = this@TasksActivity.sharedPreferences } // Use the instance created as a property
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonAddTask.setOnClickListener {
            val newTaskName = editTextTask.text.toString().trim()
            if (newTaskName.isNotEmpty()) {
                addTask(newTaskName)
                editTextTask.setText("")
            }
        }

        val buttonDeleteTasks: Button = findViewById(R.id.clearButton)
        buttonDeleteTasks.setOnClickListener {
            deleteTasks()
        }
    }
}
