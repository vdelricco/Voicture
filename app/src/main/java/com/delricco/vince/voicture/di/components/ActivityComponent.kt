package com.delricco.vince.voicture.di.components;

import com.delricco.vince.voicture.activities.MainActivity
import com.delricco.vince.voicture.activities.PreviewVoictureProjectActivity
import com.delricco.vince.voicture.activities.ProjectCreationActivity
import com.delricco.vince.voicture.di.modules.AudioModule
import com.delricco.vince.voicture.di.modules.SerDesModule
import com.delricco.vince.voicture.di.modules.SharedPrefsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AudioModule::class, SharedPrefsModule::class, SerDesModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(projectCreationActivity: ProjectCreationActivity)
    fun inject(previewVoictureProjectActivity: PreviewVoictureProjectActivity)
}
