package com.delricco.vince.voicture.di.modules

import android.content.Context
import com.delricco.vince.voicture.filestorage.FileStorageManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FileStorageModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideFileStorageManager(): FileStorageManager {
        return FileStorageManager(context)
    }
}