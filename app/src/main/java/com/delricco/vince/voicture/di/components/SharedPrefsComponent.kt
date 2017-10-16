package com.delricco.vince.voicture.di.components;

import com.delricco.vince.voicture.activities.MainActivity
import com.delricco.vince.voicture.di.modules.SharedPrefsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SharedPrefsModule::class))
interface SharedPrefsComponent {
    fun inject(mainActivity: MainActivity)
}
