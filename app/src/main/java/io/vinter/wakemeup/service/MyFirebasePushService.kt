package io.vinter.wakemeup.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import io.vinter.wakemeup.R
import android.os.PowerManager
import android.os.AsyncTask


class MyFirebasePushService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        val runnable = Runnable {
            createNotificationChannel()
            sendNotification("WAKEUP", "Wake up!", "Wake up immediately!")
            wakeupScreen()
            Toast.makeText(applicationContext, p0.toString(), Toast.LENGTH_SHORT).show()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(applicationContext, AlarmService::class.java))
            } else {
                startService(Intent(applicationContext, AlarmService::class.java))
            }
        }
        val handler = Handler(Looper.getMainLooper())
        handler.post(runnable)
    }

    private fun sendNotification(channel: String, title: String, content: String) {
        val builder = NotificationCompat.Builder(this, channel)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_friends_alarm)
                .setContentTitle(title)
                .setContentText(content)
                .setVibrate(LongArray(0))
                .setPriority(NotificationCompat.PRIORITY_MAX)
        with(NotificationManagerCompat.from(this)) {
            notify(12, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "wake me up"
            val descriptionText = "wake up notifications"
            val channel = NotificationChannel("WAKEUP", name, NotificationManager.IMPORTANCE_HIGH).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("StaticFieldLeak")
    private fun wakeupScreen() {
        object : AsyncTask<Void, Void, Exception>() {
            override fun doInBackground(vararg params: Void): Exception? {
                try {
                    val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
                    val fullWakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "wakemeup:FULL WAKE LOCK")
                    fullWakeLock.acquire() // turn on
                    try {
                        Thread.sleep(10000) // turn on duration
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    fullWakeLock.release()
                } catch (e: Exception) {
                    return e
                }
                return null
            }
        }.execute()
    }
}
