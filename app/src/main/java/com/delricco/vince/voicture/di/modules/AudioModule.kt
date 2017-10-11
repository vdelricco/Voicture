package com.delricco.vince.voicture.di.modules

import com.delricco.vince.voicture.audio.AudioPlaybackManager
import com.delricco.vince.voicture.audio.AudioRecordingManager
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
