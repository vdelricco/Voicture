package com.delricco.vince.voicture

import android.app.Application
import com.delricco.vince.voicture.di.components.AudioComponent
import com.delricco.vince.voicture.di.components.DaggerAudioComponent
import com.github.ajalt.timberkt.Timber
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber.DebugTree

class VoictureApplication : Application() {

    companion object {
        lateinit var audioComponent: AudioComponent
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        Timber.plant(DebugTree())
        audioComponent = DaggerAudioComponent.create()
    }
}
