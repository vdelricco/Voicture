package co.delric.voicture

import android.app.Application
import co.delric.voicture.di.components.ApplicationComponent
import co.delric.voicture.di.components.DaggerApplicationComponent
import co.delric.voicture.di.modules.AndroidModule
import com.github.ajalt.timberkt.Timber
import com.squareup.leakcanary.LeakCanary
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber.DebugTree
import javax.inject.Inject


class VoictureApplication : Application(), HasAndroidInjector {
    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
            Timber.plant(DebugTree())
        }

        applicationComponent = DaggerApplicationComponent
            .builder()
            .androidModule(AndroidModule(this))
            .build()

        applicationComponent.inject(this)
    }
}