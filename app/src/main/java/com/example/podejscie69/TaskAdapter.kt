package com.example.podejscie69

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}

class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    lateinit var sharedPreferences: SharedPreferences

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val taskText: TextView = itemView.findViewById(R.id.task_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.taskText.text = currentItem.taskName
        holder.checkBox.isChecked = currentItem.isCompleted

        // Set the initial state of the checkbox based on SharedPreferences
        val isChecked = sharedPreferences.getBoolean("task_${currentItem.id}_checked", false)
        holder.checkBox.isChecked = isChecked

        // Update SharedPreferences when the checkbox state changes
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("task_${currentItem.id}_checked", isChecked).apply()
        }
    }
}
