package com.delricco.vince.voicture.di.components

import com.delricco.vince.voicture.commons.sharedprefs.SavedProject
import com.delricco.vince.voicture.di.modules.SerDesModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SerDesModule::class))
interface SharedPrefsComponent {
    fun inject(savedProject: SavedProject)
}