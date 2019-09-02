package io.vinter.wakemeup.service

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.os.Build
import android.os.PowerManager
import android.os.AsyncTask
import io.vinter.wakemeup.utils.NotificationManager


class MyFirebasePushService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        val runnable = Runnable {
            NotificationManager.createNotificationChannel(this)
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

    @SuppressLint("StaticFieldLeak")
    private fun wakeupScreen() {
        object : AsyncTask<Void, Void, Exception>() {
            @SuppressLint("WakelockTimeout")
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
