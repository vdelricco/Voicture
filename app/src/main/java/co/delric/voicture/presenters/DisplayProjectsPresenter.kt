package co.delric.voicture.presenters

import co.delric.voicture.commons.sharedprefs.SavedProjects

class DisplayProjectsPresenter(private val savedProjects: SavedProjects) {
    fun getSavedProjects() = savedProjects.getSavedProjects()
}