package com.delricco.vince.voicture

import android.app.Application
import com.delricco.vince.voicture.di.components.ActivityComponent
import com.delricco.vince.voicture.di.components.DaggerActivityComponent
import com.delricco.vince.voicture.di.modules.SharedPrefsModule
import com.github.ajalt.timberkt.Timber
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber.DebugTree

class VoictureApplication : Application() {

    companion object {
        lateinit var activityComponent: ActivityComponent
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
        activityComponent = DaggerActivityComponent
                .builder()
                .sharedPrefsModule(SharedPrefsModule(applicationContext))
                .build()
    }
}
