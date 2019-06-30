package co.delric.voicture.di.modules

import android.content.Context
import co.delric.voicture.filestorage.FileStorageManager
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