package co.delric.voicture.di.components;

import co.delric.voicture.di.modules.*
import co.delric.voicture.ui.activities.EditProjectActivity
import co.delric.voicture.ui.activities.DisplayProjectsActivity
import co.delric.voicture.ui.activities.PreviewVoictureProjectActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AudioModule::class,
    SharedPrefsModule::class,
    SerDesModule::class,
    FileStorageModule::class,
    PresenterModule::class
])
interface ActivityComponent {
    fun inject(displayProjectsActivity: DisplayProjectsActivity)
    fun inject(editProjectActivity: EditProjectActivity)
    fun inject(previewVoictureProjectActivity: PreviewVoictureProjectActivity)
}