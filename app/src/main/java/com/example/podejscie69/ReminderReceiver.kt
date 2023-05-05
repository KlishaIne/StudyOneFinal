package com.example.podejscie69

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import android.util.Log


class ReminderReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_REMINDER_ID = "reminder_id"
        const val EXTRA_REMINDER_TITLE = "reminder_title"
        const val CHANNEL_ID = "reminder_channel"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotificationReceiver", "onReceive called with reminderId: $EXTRA_REMINDER_ID")

        val reminderId = intent.getIntExtra(EXTRA_REMINDER_ID, 0)
        val reminderTitle = intent.getStringExtra(EXTRA_REMINDER_TITLE)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = buildNotification(context, reminderTitle)
        notificationManager.notify(reminderId, notification)
    }

    private fun buildNotification(context: Context, reminderTitle: String?): Notification {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your app's icon
            .setContentTitle(reminderTitle)
            .setContentText("It's time for your reminder!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        return builder.build()
    }
}
