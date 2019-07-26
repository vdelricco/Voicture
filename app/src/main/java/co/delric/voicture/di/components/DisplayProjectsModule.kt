package co.delric.voicture.di.components

import co.delric.voicture.ui.activities.DisplayProjectsActivity
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dagger.Binds

@Module(subcomponents = [DisplayProjectsSubcomponent::class])
abstract class DisplayProjectsModule {
    @Binds
    @IntoMap
    @ClassKey(DisplayProjectsActivity::class)
    internal abstract fun bindYourAndroidInjectorFactory(
        factory: DisplayProjectsSubcomponent.Factory
    ): AndroidInjector.Factory<*>
}