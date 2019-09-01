package io.vinter.wakemeup.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.*
import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.PreferencesRepository
import io.vinter.wakemeup.utils.NotificationManager

class AlarmService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var am: AudioManager
    private lateinit var vibrator: Vibrator
    private var volumeBefore = 0
    private var needNotification = false

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
            am.setStreamVolume(AudioManager.STREAM_MUSIC, getVolumeLevel(repository, am), 0)
            if (repository.getVibrationNeed()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 550, 320, 550, 320, 550, 320), 1))
                } else {
                    vibrator.vibrate(longArrayOf(0, 550, 320, 550, 320, 550, 320), 1)
                }
            }
            mediaPlayer.start()
            Handler().postDelayed({
                needNotification = true
                this.stopSelf()
            }, 25000)
        }
        handler.post(runnable)
    }

    private fun startForeground() {
        val channelId = "WAKEUP"
        startForeground(101, NotificationManager.getOngoingNotification(channelId, this))
    }

    override fun onDestroy() {
        vibrator.cancel()
        mediaPlayer.stop()
        if (needNotification) NotificationManager.sendNotification("WAKEUP", getString(R.string.notification_missed_title), getString(R.string.notification_missed), this)
        am.setStreamVolume(AudioManager.STREAM_MUSIC, volumeBefore, 0)
        super.onDestroy()
    }

    private fun getVolumeLevel(prefs: PreferencesRepository, audioManager: AudioManager): Int{
        return when (prefs.getVolume()){
            R.id.volume_set_low -> 0
            R.id.volume_set_medium -> audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 2
            R.id.volume_set_max -> audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            else -> audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 2
        }
    }

}
