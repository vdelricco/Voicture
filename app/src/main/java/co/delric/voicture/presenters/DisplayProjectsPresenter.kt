package co.delric.voicture.presenters

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import co.delric.voicture.commons.serialization.VoictureProjectSerDes
import co.delric.voicture.commons.sharedprefs.SavedProjects
import co.delric.voicture.di.components.ActivityScope
import co.delric.voicture.filestorage.FileStorageManager
import co.delric.voicture.intents.Intents
import co.delric.voicture.models.Voicture
import co.delric.voicture.models.VoictureProject
import co.delric.voicture.ui.activities.DisplayProjectsView
import com.github.ajalt.timberkt.Timber
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class DisplayProjectsPresenter @Inject constructor(
    private val savedProjects: SavedProjects,
    private val fileStorageManager: FileStorageManager,
    private val voictureProjectSerDes: VoictureProjectSerDes
) {
    var projectNameToCreate = ""

    fun getSavedProjects() = savedProjects.getSavedProjects()
    fun projectExists(projectName: String) = savedProjects.projectExists(projectName)
    fun getJsonForProject(voictureProject: VoictureProject) = voictureProjectSerDes.toJson(voictureProject)

    @SuppressLint("CheckResult")
    fun createProjectFromUris(activity: AppCompatActivity, selectedImageUriList: List<Uri>) {
        val voictureArrayList = ArrayList<Voicture>()
        var index = 0
        var error = false

        fileStorageManager.createTempProjectAudioFiles(selectedImageUriList.size)
            .compose(AndroidLifecycle.createLifecycleProvider(activity).bindUntilEvent(Lifecycle.Event.ON_PAUSE))
            .doOnError { t: Throwable ->
                Timber.e { t.toString() }
                error = true
            }
            .doOnComplete {
                if (!error) {
                    activity.runOnUiThread {
                        activity.startActivity(
                            Intents.createProjectIntent(voictureProjectSerDes.toJson(
                                VoictureProject(voictureArrayList, projectNameToCreate)), activity))
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .subscribe { file ->
                voictureArrayList.add(Voicture(selectedImageUriList[index], file))
                index++
            }
    }

    fun onNewProjectClicked(displayProjectsView: DisplayProjectsView) {
        displayProjectsView.startProjectCreationFlow()
    }

    fun onCreateProject(projectName: String, displayProjectsView: DisplayProjectsView) = when {
        projectName.isEmpty() -> displayProjectsView.showProjectNameNeeded()
        projectExists(projectName) -> displayProjectsView.showProjectNameTaken()
        else -> displayProjectsView.sendChoosePhotosIntent()
    }
}