package com.delricco.vince.voicture.di.components

import com.delricco.vince.voicture.activities.PreviewVoictureProjectActivity
import com.delricco.vince.voicture.activities.ProjectCreationActivity
import com.delricco.vince.voicture.di.modules.AudioModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AudioModule::class
))
interface AudioComponent {
    fun inject(previewVoictureProjectActivity: PreviewVoictureProjectActivity)
    fun inject(projectCreationActivity: ProjectCreationActivity)
}
