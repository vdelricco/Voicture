package com.delricco.vince.voicture.commons.sharedprefs

import android.content.Context
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.VoictureApplication
import com.delricco.vince.voicture.commons.serialization.VoictureProjectSerDes
import com.delricco.vince.voicture.models.VoictureProject
import javax.inject.Inject

class SavedProject(context: Context) {
    companion object {
        private val SAVED_PROJECT_KEY = "com.delricco.vince.voicture.commons.sharedprefs.SavedProject.SAVED_PROJECT_KEY"
    }

    private val sharedPrefs = context.getSharedPreferences(context.getString(R.string.saved_project_pref), Context.MODE_PRIVATE)

    @Inject protected lateinit var voictureProjectSerDes: VoictureProjectSerDes

    init {
        VoictureApplication.sharedPrefsComponent.inject(this)
    }

    fun hasSavedProject() = sharedPrefs.getString(SAVED_PROJECT_KEY, "") != ""
    fun getSavedProject() = voictureProjectSerDes.fromJson(sharedPrefs.getString(SAVED_PROJECT_KEY, ""))
    fun saveProject(project: VoictureProject) = sharedPrefs.edit().putString(SAVED_PROJECT_KEY, voictureProjectSerDes.toJson(project)).apply()
    fun clear() = sharedPrefs.edit().clear().apply()
}
