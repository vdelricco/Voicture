package co.delric.voicture.di.components

import co.delric.voicture.ui.activities.DisplayProjectsActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface DisplayProjectsSubcomponent : AndroidInjector<DisplayProjectsActivity> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<DisplayProjectsActivity>
}