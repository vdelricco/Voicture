package co.delric.voicture.ui.activities

import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import co.delric.voicture.R
import co.delric.voicture.VoictureApplication
import co.delric.voicture.commons.serialization.VoictureProjectSerDes
import co.delric.voicture.commons.sharedprefs.SavedProjects
import co.delric.voicture.filestorage.FileStorageManager
import co.delric.voicture.intents.Intents
import co.delric.voicture.models.Voicture
import co.delric.voicture.models.VoictureProject
import co.delric.voicture.presenters.DisplayProjectsPresenter
import co.delric.voicture.ui.adapters.VoictureProjectDelegateAdapter
import co.delric.voicture.ui.fragments.DisplaySavedProjectsFragment
import com.github.ajalt.timberkt.Timber
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_display_projects.*
import javax.inject.Inject

class DisplayProjectsActivity : AppCompatActivity(),
        DisplaySavedProjectsFragment.VoictureListProvider,
        VoictureProjectDelegateAdapter.OnViewSelectedListener {
    companion object {
        const val PICK_IMAGES = 1
    }

    @Inject protected lateinit var savedProjectsPrefs: SavedProjects
    @Inject protected lateinit var voictureProjectSerDes: VoictureProjectSerDes
    @Inject protected lateinit var fileStorageManager: FileStorageManager
    @Inject protected lateinit var displayProjectsPresenter: DisplayProjectsPresenter

    private val provider = AndroidLifecycle.createLifecycleProvider(this)
    private lateinit var projectNameToCreate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_projects)
        VoictureApplication.activityComponent.inject(this)
        setSupportActionBar(toolbar)
        createProjectFab.setOnClickListener { startProjectCreationFlow() }
        if (savedInstanceState == null) changeFragment(DisplaySavedProjectsFragment(), false)
    }

    override fun getVoictureProjectList() = displayProjectsPresenter.getSavedProjects()

    override fun onItemSelected(project: VoictureProject) =
        startActivity(Intents.createProjectIntent(voictureProjectSerDes.toJson(project), this))

    private fun startProjectCreationFlow() {
        val inputEditText = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_TEXT
            setText(getString(R.string.default_project_name, savedProjectsPrefs.getSavedProjects().size.toString()))
        }

        AlertDialog.Builder(this).apply {
            setTitle(R.string.choose_project_name)
            setView(inputEditText)
            setPositiveButton("Create") { _, _ ->
                val chosenName = inputEditText.text.toString()
                if (savedProjectsPrefs.projectExists(chosenName)) {
                    Toast.makeText(applicationContext, "Name is already taken", Toast.LENGTH_LONG).show()
                } else {
                    projectNameToCreate = chosenName
                    sendChoosePhotosIntent()
                }
            }
            setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            show()
        }
    }

    private fun sendChoosePhotosIntent() {
        if (projectNameToCreate.isEmpty()) {
            Toast.makeText(applicationContext, "Need a project name first!", Toast.LENGTH_LONG).show()
            return
        }

        startActivityForResult(Intent.createChooser(Intents.CHOOSE_MULTIPLE_PHOTOS, "Select Pictures"), PICK_IMAGES)
    }

    @SuppressLint("CheckResult")
    override fun onActivityResult(requestCode: Int, resultCode: Int, dataIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, dataIntent)
        if (requestCode != PICK_IMAGES || resultCode != RESULT_OK || dataIntent == null) return

        val selectedImageUriList = mutableListOf<Uri>()

        dataIntent.apply {
            data?.let {
                Timber.d { "Adding $it to selected image uri list" }
                selectedImageUriList.add(it)
            }

            clipData?.let {
                (0 until it.itemCount).mapTo(selectedImageUriList) { index ->
                    Timber.d { "Adding ${it.getItemAt(index).uri} to selected image uri list" }
                    it.getItemAt(index).uri
                }
            }
        }

        val voictureArrayList = ArrayList<Voicture>()
        var index = 0
        var error = false
        fileStorageManager.createTempProjectAudioFiles(selectedImageUriList.size)
            .compose(provider.bindUntilEvent(Lifecycle.Event.ON_PAUSE))
            .doOnError { t: Throwable ->
                Timber.e { t.toString() }
                error = true
            }
            .doOnComplete {
                if (!error) {
                    runOnUiThread {
                        startActivity(
                            Intents.createProjectIntent(voictureProjectSerDes.toJson(
                                VoictureProject(voictureArrayList, projectNameToCreate)), this))
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .subscribe { file ->
                voictureArrayList.add(Voicture(selectedImageUriList[index], file))
                index++
            }
    }

    override fun onCreateOptionsMenu(menu: Menu) = menuInflater.run {
        inflate(R.menu.menu_main, menu)
        true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_new_project -> {
            startProjectCreationFlow()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun changeFragment(f: Fragment, addToBackStack: Boolean) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragment_container, f)
        if (addToBackStack) addToBackStack(null)
    }.commit()
}
