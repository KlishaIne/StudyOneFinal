package com.example.podejscie69

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.podejscie69.databinding.ItemActivityLogBinding


class ActivityLogsAdapter(private val activityLogs: List<ActivityLog>) : RecyclerView.Adapter<ActivityLogsAdapter.ActivityLogViewHolder>() {

    // Define a view holder for the activity logs recycler view
    inner class ActivityLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Bind the UI components in the item layout to properties of this view holder
        private val binding = ItemActivityLogBinding.bind(itemView)
        val tvActivityDescription: TextView = binding.tvActivityDescription
        val tvActivityTimestamp: TextView = binding.tvActivityTimestamp

        // Bind the data in an activity log to the UI components in the item layout
        fun bind(activityLog: ActivityLog) {
            tvActivityDescription.text = activityLog.activity
            tvActivityTimestamp.text = activityLog.timestamp.toString()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityLogViewHolder {
        // Inflate the item layout and create a new view holder
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_activity_log, parent, false)
        return ActivityLogViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ActivityLogViewHolder, position: Int) {
        // Retrieve the current activity log and bind it to the view holder
        val currentItem = activityLogs[position]
        holder.bind(currentItem)
    }

    // Return the number of items in the data set
    override fun getItemCount(): Int {
        return activityLogs.size
    }
}
