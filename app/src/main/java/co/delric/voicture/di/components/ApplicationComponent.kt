package co.delric.voicture.di.components

import co.delric.voicture.di.modules.AndroidModule
import dagger.Component
import javax.inject.Scope

/**
 * Identifies a type that the injector only instantiates once.
 *
 * @see javax.inject.Scope @Scope
 */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@ApplicationScope
@Component(modules = [AndroidModule::class])
interface ApplicationComponent {
    fun activityComponent(): ActivityComponent.Builder

    @Component.Builder
    interface Builder {
        fun androidModule(module: AndroidModule): Builder
        fun build(): ApplicationComponent
    }
}