package io.vinter.wakemeup.service

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.*
import android.support.v4.app.NotificationCompat
import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.PreferencesRepository

class AlarmService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var am: AudioManager
    private lateinit var vibrator: Vibrator
    private var volumeBefore = 0

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented") as Throwable
    }

    override fun onCreate() {
        super.onCreate()
        startForeground()
        am = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer.isLooping = true
        val handler = Handler(Looper.getMainLooper())
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val repository = PreferencesRepository(this)

        val runnable = Runnable {
            volumeBefore = am.getStreamVolume(AudioManager.STREAM_MUSIC)
            am.isSpeakerphoneOn = true
            am.setStreamVolume(AudioManager.STREAM_MUSIC, getVolumeLevel(repository), 0)
            if (repository.getVibrationNeed()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 550, 320, 550, 320, 550, 320), 1))
                } else {
                    vibrator.vibrate(longArrayOf(0, 550, 320, 550, 320, 550, 320), 1)
                }
            }
            mediaPlayer.start()
            Handler().postDelayed({ this.stopSelf() }, 25000)
        }
        handler.post(runnable)
    }

    private fun startForeground() {
        val channelId = "WAKEUP"
        val notificationBuilder = NotificationCompat.Builder(this, channelId )
        val notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_friends_alarm)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()
        startForeground(101, notification)
    }

    override fun onDestroy() {
        vibrator.cancel()
        mediaPlayer.stop()
        am.setStreamVolume(AudioManager.STREAM_MUSIC, volumeBefore, 0)
        super.onDestroy()
    }

    private fun getVolumeLevel(prefs: PreferencesRepository): Int{
        return when (prefs.getVolume()){
            R.id.volume_set_low -> 0
            R.id.volume_set_medium -> 8
            R.id.volume_set_max -> 15
            else -> 8
        }
    }

}
