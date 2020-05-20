package com.example.background_tts_stt

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import java.lang.Exception

class MainActivity : FlutterActivity() {

    private val TAG = "MainActivity"

    companion object {
        private var channel: MethodChannel? = null
        private var eventChannel: EventChannel? = null
        var eventSink: EventChannel.EventSink? = null

        @JvmStatic
        var mFlutterEngine: FlutterEngine? = null

        @JvmStatic
        var binaryMessenger: BinaryMessenger? = null

        @JvmStatic
        fun registerWith(messenger: BinaryMessenger?, context: Context?) {
            messenger?.let {
                channel = MethodChannel(messenger, "speech_listener_channel")
                channel?.setMethodCallHandler { call, result ->
                    when (call.method) {
                        "startService" -> {
                            context?.let {
                                if (!isMyServiceRunning(SpeechListenService::class.java, context)) {
                                    try {
                                        context.startService(Intent(context, SpeechListenService::class.java))
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                    result.success("Started Speech listener service.")
                                } else {
                                    result.success("Speech listener service already running.")
                                }
                            }
                        }
                        "stopService" -> {
                            eventSink?.endOfStream()
                            eventSink = null
                            
                            context?.let {
                                try {
                                    context.stopService(Intent(context, SpeechListenService::class.java))
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                                result.success("Stopped Speech listener service.")
                            }
                        }
                        else -> result.notImplemented()
                    }
                }

                eventChannel = EventChannel(messenger, "speech_listener_stream")
                eventChannel?.setStreamHandler(object : EventChannel.StreamHandler {
                    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                        eventSink = events
                    }

                    override fun onCancel(arguments: Any?) {

                    }
                })
            }
        }

        @JvmStatic
        fun isMyServiceRunning(serviceClass: Class<*>, context: Context): Boolean {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableAutoStart()

        flutterEngine?.let {

            mFlutterEngine = flutterEngine

            it.dartExecutor.binaryMessenger.let { messenger ->
                binaryMessenger = messenger
                registerWith(messenger, this)
            }
        }
    }
}
