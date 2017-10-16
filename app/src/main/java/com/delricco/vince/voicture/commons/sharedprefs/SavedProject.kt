package com.delricco.vince.voicture.commons.sharedprefs

import android.content.Context
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.commons.serialization.VoictureProjectSerDes
import com.delricco.vince.voicture.models.VoictureProject

class SavedProject(context: Context) {
    private val SAVED_PROJECT_KEY = "com.delricco.vince.voicture.commons.sharedprefs.SavedProject.SAVED_PROJECT_KEY"
    private val sharedPrefs = context.getSharedPreferences(context.getString(R.string.saved_project_pref), Context.MODE_PRIVATE)

    fun isSavedProject(): Boolean = (sharedPrefs.getString(SAVED_PROJECT_KEY, "") != "")

    fun getSavedProject(): VoictureProject {
        val voictureProjectJson = sharedPrefs.getString(SAVED_PROJECT_KEY, "")
        return VoictureProjectSerDes.fromJson(voictureProjectJson)
    }

    fun saveProject(project: VoictureProject) {
        sharedPrefs.edit().putString(SAVED_PROJECT_KEY, VoictureProjectSerDes.toJson(project)).apply()
    }
}
