package com.example.background_tts_stt

import com.sac.speech.Logger
import com.sac.speech.Speech
import io.flutter.app.FlutterApplication

class MainApplication : FlutterApplication(){
    override fun onCreate() {
        super.onCreate()
        Speech.init(this, packageName, 10000L, 1500L)
        Logger.setLogLevel(Logger.LogLevel.DEBUG)
    }
}