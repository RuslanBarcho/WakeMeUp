package io.vinter.wakemeup.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.*
import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.preferences.PreferencesRepository
import io.vinter.wakemeup.data.volume.VolumeStates
import io.vinter.wakemeup.utils.NotificationManager
import org.koin.android.ext.android.get

class AlarmService : Service() {

    private val preferences: PreferencesRepository = get()
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
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)

        mediaPlayer.isLooping = true
        val handler = Handler(Looper.getMainLooper())

        val runnable = Runnable {
            volumeBefore = am.getStreamVolume(AudioManager.STREAM_MUSIC)
            am.isSpeakerphoneOn = true
            am.setStreamVolume(AudioManager.STREAM_MUSIC, getVolumeLevel(preferences, am), 0)
            if (preferences.getVibrationNeed()){
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
        startForeground(101, NotificationManager.getOngoingNotification(this))
    }

    override fun onDestroy() {
        vibrator.cancel()
        mediaPlayer.stop()
        if (needNotification) NotificationManager.sendNotification(getString(R.string.notification_missed_title), getString(R.string.notification_missed), this)
        am.setStreamVolume(AudioManager.STREAM_MUSIC, volumeBefore, 0)
        super.onDestroy()
    }

    private fun getVolumeLevel(prefs: PreferencesRepository, audioManager: AudioManager): Int{
        return when (prefs.getVolume()){
            VolumeStates.OFF -> 0
            VolumeStates.MEDIUM -> audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 2
            VolumeStates.MAX -> audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        }
    }

}
