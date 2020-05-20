package com.example.background_tts_stt

import android.Manifest.permission
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.AudioManager.ADJUST_MUTE
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.sac.speech.GoogleVoiceTypingDisabledException
import com.sac.speech.Speech
import com.sac.speech.Speech.stopDueToDelay
import com.sac.speech.SpeechDelegate
import com.sac.speech.SpeechRecognitionNotAvailable
import com.tbruyelle.rxpermissions.RxPermissions
import java.util.*

class SpeechListenService : Service(), SpeechDelegate, stopDueToDelay {

    private val TAG = "SpeechListenService"
    private val direction = ADJUST_MUTE


    override fun onCreate() {
        super.onCreate()

        MainActivity.binaryMessenger?.let {
            Log.i(TAG, "$TAG service running.")
            MainActivity.registerWith(it, this)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        try {
            if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
                (Objects.requireNonNull(
                        getSystemService(Context.AUDIO_SERVICE)) as AudioManager).adjustStreamVolume(AudioManager.STREAM_SYSTEM, direction, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Speech.init(this)
        delegate = this
        Speech.getInstance().setListener(this)
        if (Speech.getInstance().isListening) {
            Speech.getInstance().stopListening()
            muteBeepSoundOfRecorder()
        } else {
            System.setProperty("rx.unsafe-disable", "True")
            RxPermissions.getInstance(this).request(permission.RECORD_AUDIO).subscribe { granted: Boolean ->
                if (granted) {
                    try {
                        Speech.getInstance().stopTextToSpeech()
                        Speech.getInstance().startListening(null, this)
                    } catch (exc: SpeechRecognitionNotAvailable) {
                        Log.e(TAG, "${exc.message}")
                    } catch (exc: GoogleVoiceTypingDisabledException) {
                        Log.e(TAG, "${exc.message}")
                    }
                } else {
                    Toast.makeText(this, R.string.permission_required, Toast.LENGTH_LONG).show()
                }
            }
            muteBeepSoundOfRecorder()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartOfSpeech() {}
    override fun onSpeechRmsChanged(value: Float) {}
    override fun onSpeechPartialResults(results: List<String>) {
        for (partial in results) {
            if (partial.isNotEmpty())
                MainActivity.eventSink?.success(SpeechResult(partial, true).toString())
        }
    }

    override fun onSpeechResult(result: String) {
        if (result.isNotEmpty()) {
            MainActivity.eventSink?.success(SpeechResult(result, false).toString())
        }
    }

    override fun onSpecifiedCommandPronounced(event: String) {
        try {
            if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
                (Objects.requireNonNull(
                        getSystemService(Context.AUDIO_SERVICE)) as AudioManager).adjustStreamVolume(AudioManager.STREAM_SYSTEM, direction, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (Speech.getInstance().isListening) {
            muteBeepSoundOfRecorder()
            Speech.getInstance().stopListening()
        } else {
            RxPermissions.getInstance(this).request(permission.RECORD_AUDIO).subscribe { granted: Boolean ->
                if (granted) {
                    try {
                        Speech.getInstance().stopTextToSpeech()
                        Speech.getInstance().startListening(null, this)
                    } catch (exc: SpeechRecognitionNotAvailable) {
                        Log.e(TAG, "${exc.message}")
                    } catch (exc: GoogleVoiceTypingDisabledException) {
                        Log.e(TAG, "${exc.message}")
                    }
                } else {
                    Toast.makeText(this, R.string.permission_required, Toast.LENGTH_LONG).show()
                }
            }
            muteBeepSoundOfRecorder()
        }
    }

    /**
     * Function to remove the beep sound of voice recognizer.
     */
    private fun muteBeepSoundOfRecorder() {
        (getSystemService(Context.AUDIO_SERVICE) as AudioManager).let { audioManager ->
            audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, direction, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, direction, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, direction, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_RING, direction, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, direction, 0)
        }
    }

    override fun onTaskRemoved(rootIntent: Intent) { //Restarting the service if it is removed.
        val service = PendingIntent.getService(applicationContext, Random().nextInt(),
                Intent(applicationContext, SpeechListenService::class.java), PendingIntent.FLAG_ONE_SHOT)
        val alarmManager = (getSystemService(Context.ALARM_SERVICE) as AlarmManager)
        alarmManager[AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000] = service
        super.onTaskRemoved(rootIntent)
    }

    companion object {
        var delegate: SpeechDelegate? = null
    }
}