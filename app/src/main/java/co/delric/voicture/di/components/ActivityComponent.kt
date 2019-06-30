package co.delric.voicture.di.components;

import co.delric.voicture.activities.EditProjectActivity
import co.delric.voicture.activities.MainActivity
import co.delric.voicture.activities.PreviewVoictureProjectActivity
import co.delric.voicture.di.modules.AudioModule
import co.delric.voicture.di.modules.FileStorageModule
import co.delric.voicture.di.modules.SerDesModule
import co.delric.voicture.di.modules.SharedPrefsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AudioModule::class, SharedPrefsModule::class, SerDesModule::class, FileStorageModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(editProjectActivity: EditProjectActivity)
    fun inject(previewVoictureProjectActivity: PreviewVoictureProjectActivity)
}