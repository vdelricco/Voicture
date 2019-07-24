package co.delric.voicture.di.modules

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
open class ActivityModule(private val activity: Activity) {
    @Provides
    fun activity(): Activity = activity
}