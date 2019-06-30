package co.delric.voicture.di.modules

import co.delric.voicture.audio.AudioPlaybackManager
import co.delric.voicture.audio.AudioRecordingManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AudioModule {
    @Provides
    @Singleton
    fun provideAudioPlaybackManager(): AudioPlaybackManager {
        return AudioPlaybackManager()
    }

    @Provides
    @Singleton
    fun provideAudioRecordingManager(): AudioRecordingManager {
        return AudioRecordingManager()
    }
}