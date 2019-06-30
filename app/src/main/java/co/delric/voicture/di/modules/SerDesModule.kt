package co.delric.voicture.di.modules

import co.delric.voicture.commons.serialization.VoictureProjectSerDes
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
