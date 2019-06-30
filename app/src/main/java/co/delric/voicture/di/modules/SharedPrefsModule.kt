package co.delric.voicture.di.modules

import android.content.Context
import co.delric.voicture.commons.sharedprefs.SavedProjects
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefsModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideSavedProjectPref(): SavedProjects {
        return SavedProjects(context)
    }
}