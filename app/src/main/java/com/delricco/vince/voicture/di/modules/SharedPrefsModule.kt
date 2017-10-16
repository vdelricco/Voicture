package com.delricco.vince.voicture.di.modules

import android.content.Context
import com.delricco.vince.voicture.commons.sharedprefs.SavedProject
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefsModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideSavedProjectPref(): SavedProject {
        return SavedProject(context)
    }
}
