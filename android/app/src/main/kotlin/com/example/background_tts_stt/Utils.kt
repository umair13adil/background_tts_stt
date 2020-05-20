package com.example.background_tts_stt

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import io.flutter.embedding.android.FlutterActivity

fun FlutterActivity.enableAutoStart() {
    for (intent in Constants.AUTO_START_INTENTS) {
        if (packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            MaterialDialog.Builder(this).title(R.string.enable_auto_start)
                    .content(R.string.ask_permission)
                    .theme(Theme.LIGHT)
                    .positiveText(getString(R.string.allow))
                    .onPositive { dialog: MaterialDialog?, which: DialogAction? ->
                        try {
                            for (intent1 in Constants.AUTO_START_INTENTS) if (packageManager.resolveActivity(intent1, PackageManager.MATCH_DEFAULT_ONLY)
                                    != null) {
                                startActivity(intent1)
                                break
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    .show()
            break
        }
    }
}