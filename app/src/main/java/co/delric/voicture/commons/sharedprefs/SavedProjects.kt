package co.delric.voicture.commons.sharedprefs

import android.content.Context
import co.delric.voicture.R
import co.delric.voicture.commons.serialization.VoictureProjectSerDes
import co.delric.voicture.di.components.ApplicationScope
import co.delric.voicture.models.VoictureProject
import javax.inject.Inject

@ApplicationScope
class SavedProjects @Inject constructor(
    context: Context,
    private val voictureProjectSerDes: VoictureProjectSerDes
) {
    companion object {
        private const val SAVED_PROJECTS_KEY = "co.delric.voicture.commons.sharedprefs.SavedProjects.SAVED_PROJECTS_KEY"
    }

    private val sharedPrefs = context.getSharedPreferences(context.getString(R.string.saved_projects_pref), Context.MODE_PRIVATE)

    fun getSavedProjects() = if (hasSavedProject()) {
        voictureProjectSerDes.listFromJson(sharedPrefs.getString(SAVED_PROJECTS_KEY, "")!!)
    } else {
        arrayListOf()
    }

    private fun hasSavedProject() = sharedPrefs.getString(SAVED_PROJECTS_KEY, "") != ""

    fun projectExists(testName: String) = getSavedProjects().find { it.name == testName } != null

    fun saveProject(project: VoictureProject) = getSavedProjects().toMutableList().let {
        if (projectExists(project.name)) {
            it[getIndexByName(project.name)] = project
        } else {
            it.add(project)
        }

        sharedPrefs.edit().putString(SAVED_PROJECTS_KEY, voictureProjectSerDes.listToJson(it)).apply()
    }

    private fun getIndexByName(testName: String) = getSavedProjects().let { projects ->
        projects.indices.firstOrNull { projects[it].name == testName } ?: -1
    }

    fun clear() = sharedPrefs.edit().clear().apply()
}