package com.delricco.vince.voicture

import android.app.Application
import com.github.ajalt.timberkt.Timber

class VoictureApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(timber.log.Timber.DebugTree())
    }
}