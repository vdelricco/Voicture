package com.delricco.vince.voicture

import android.app.Application
import com.github.ajalt.timberkt.Timber
import timber.log.Timber.DebugTree

class VoictureApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}
