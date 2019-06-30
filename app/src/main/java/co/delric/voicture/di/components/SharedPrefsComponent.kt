package co.delric.voicture.di.components

import co.delric.voicture.commons.sharedprefs.SavedProjects
import co.delric.voicture.di.modules.SerDesModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SerDesModule::class))
interface SharedPrefsComponent {
    fun inject(savedProjects: SavedProjects)
}