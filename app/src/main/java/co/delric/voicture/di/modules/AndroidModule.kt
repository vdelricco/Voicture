package co.delric.voicture.di.modules

import android.content.Context
import co.delric.voicture.VoictureApplication
import dagger.Module
import dagger.Provides

@Module
open class AndroidModule(private val voictureApplication: VoictureApplication) {
    @Provides
    fun context(): Context = voictureApplication.applicationContext
}