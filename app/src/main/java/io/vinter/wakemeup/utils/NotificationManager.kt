package io.vinter.wakemeup.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.vinter.wakemeup.R
import io.vinter.wakemeup.ui.main.MainActivity

object NotificationManager {

    private const val mainChannelId = "WAKEUP"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "wake me up"
            val descriptionText = "wake up notifications"
            val channel = NotificationChannel("WAKEUP", name, NotificationManager.IMPORTANCE_HIGH).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(title: String, content: String, context: Context) {
        val builder = NotificationCompat.Builder(context, mainChannelId)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_friends_alarm)
                .setContentTitle(title)
                .setContentText(content)
                .setVibrate(LongArray(0))
                .setPriority(NotificationCompat.PRIORITY_MAX)
        with(NotificationManagerCompat.from(context)) {
            notify(12, builder.build())
        }
    }

    fun getOngoingNotification(context: Context): Notification {

        val intent = Intent(context, MainActivity::class.java)
        val pending = PendingIntent.getActivity(context, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(context, mainChannelId).setOngoing(true)
                .setContentTitle(context.getString(R.string.notification_ongoing_title))
                .setContentText(context.getString(R.string.notification_ongoing))
                .setSmallIcon(R.drawable.ic_friends_alarm)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setContentIntent(pending)
                .build()
    }

}