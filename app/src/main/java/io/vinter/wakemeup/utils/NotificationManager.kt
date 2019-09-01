package io.vinter.wakemeup.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import io.vinter.wakemeup.R

object NotificationManager {

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

    fun sendNotification(channel: String, title: String, content: String, context: Context) {
        val builder = NotificationCompat.Builder(context, channel)
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

}