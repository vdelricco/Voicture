package co.delric.voicture.commons.sharedprefs

import android.content.Context
import co.delric.voicture.R
import co.delric.voicture.VoictureApplication
import co.delric.voicture.commons.serialization.VoictureProjectSerDes
import co.delric.voicture.models.VoictureProject
import javax.inject.Inject

class SavedProjects(context: Context) {
    companion object {
        private const val SAVED_PROJECTS_KEY = "co.delric.voicture.commons.sharedprefs.SavedProjects.SAVED_PROJECTS_KEY"
    }

    private val sharedPrefs = context.getSharedPreferences(context.getString(R.string.saved_projects_pref), Context.MODE_PRIVATE)

    @Inject protected lateinit var voictureProjectSerDes: VoictureProjectSerDes

    init {
        VoictureApplication.sharedPrefsComponent.inject(this)
    }

    fun hasSavedProject() = sharedPrefs.getString(SAVED_PROJECTS_KEY, "") != ""

    fun getSavedProjects(): List<VoictureProject> {
        return if (hasSavedProject()) {
            voictureProjectSerDes.listFromJson(sharedPrefs.getString(SAVED_PROJECTS_KEY, "")!!)
        } else {
            arrayListOf()
        }
    }

    fun getIndexByName(testName: String): Int {
        val projects = getSavedProjects()
        return projects.indices.firstOrNull { projects[it].name == testName } ?: -1
    }

    fun saveProject(project: VoictureProject) {
        val projectList = getSavedProjects() as ArrayList
        val currentIndex = getIndexByName(project.name)

        if (currentIndex != -1) {
            projectList[currentIndex] = project
        } else {
            projectList.add(project)
        }

        sharedPrefs.edit().putString(SAVED_PROJECTS_KEY, voictureProjectSerDes.listToJson(projectList)).apply()
    }

    fun clear() = sharedPrefs.edit().clear().apply()
}
