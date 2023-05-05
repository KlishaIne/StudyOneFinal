package com.example.podejscie69

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class ReminderAdapter(
    private val reminderStorage: ReminderStorage,
    private val onDeleteClickListener: (Reminder) -> Unit
) : ListAdapter<Reminder, ReminderAdapter.ReminderViewHolder>(ReminderDiffCallback()) {

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reminderTitle: TextView = itemView.findViewById(R.id.reminder_title)
        val reminderDateTime: TextView = itemView.findViewById(R.id.reminder_date_time)
        val reminderSwitch: Switch = itemView.findViewById(R.id.reminder_switch)
        val reminderDeleteButton: ImageButton = itemView.findViewById(R.id.reminder_delete_button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.reminderDeleteButton.setOnClickListener {
            onDeleteClickListener(currentItem)
        }
        holder.reminderTitle.text = currentItem.title
        holder.reminderDateTime.text = currentItem.dateTimeFormatted
        holder.reminderSwitch.setOnCheckedChangeListener { _, isChecked ->
            getItem(position).isEnabled = isChecked
            if (isChecked) {
                reminderStorage.scheduleReminder(holder.itemView.context, currentItem)
            } else {
                reminderStorage.cancelReminder(holder.itemView.context, currentItem)
            }
        }

    }
}

class ReminderDiffCallback : DiffUtil.ItemCallback<Reminder>() {
    override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem == newItem
    }

}
