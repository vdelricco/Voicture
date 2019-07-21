package co.delric.voicture.di.modules

import co.delric.voicture.commons.serialization.VoictureProjectSerDes
import co.delric.voicture.commons.sharedprefs.SavedProjects
import co.delric.voicture.filestorage.FileStorageManager
import co.delric.voicture.presenters.DisplayProjectsPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun displayProjectsPresenter(
        savedProjects: SavedProjects,
        fileStorageManager: FileStorageManager,
        voictureProjectSerDes: VoictureProjectSerDes
    ): DisplayProjectsPresenter = DisplayProjectsPresenter(savedProjects, fileStorageManager, voictureProjectSerDes)
}