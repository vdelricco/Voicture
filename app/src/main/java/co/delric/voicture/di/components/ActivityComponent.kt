package co.delric.voicture.di.components;

import co.delric.voicture.di.modules.ActivityModule
import co.delric.voicture.ui.activities.DisplayProjectsActivity
import co.delric.voicture.ui.activities.EditProjectActivity
import co.delric.voicture.ui.activities.PreviewVoictureProjectActivity
import dagger.Subcomponent
import javax.inject.Scope

/**
 * Identifies a scope whose lifecycle mirrors that of an Activity
 *
 * @see javax.inject.Scope @Scope
 */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    @Subcomponent.Builder
    interface Builder {
        fun activityModule(activityModule: ActivityModule): Builder
        fun build(): ActivityComponent
    }

    fun inject(displayProjectsActivity: DisplayProjectsActivity)
    fun inject(editProjectActivity: EditProjectActivity)
    fun inject(previewVoictureProjectActivity: PreviewVoictureProjectActivity)
}