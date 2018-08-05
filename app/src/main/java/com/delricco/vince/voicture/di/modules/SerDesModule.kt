package com.delricco.vince.voicture.di.modules

import com.delricco.vince.voicture.commons.serialization.VoictureProjectSerDes
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SerDesModule {
    @Provides
    fun provideVoictureProjectSerDes(): VoictureProjectSerDes {
        return VoictureProjectSerDes()
    }
}
