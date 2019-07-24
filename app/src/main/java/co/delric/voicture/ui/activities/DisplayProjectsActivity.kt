package co.delric.voicture.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.delric.voicture.R
import co.delric.voicture.VoictureApplication
import co.delric.voicture.di.modules.ActivityModule
import co.delric.voicture.intents.Intents
import co.delric.voicture.models.VoictureProject
import co.delric.voicture.presenters.DisplayProjectsPresenter
import co.delric.voicture.ui.adapters.VoictureProjectDelegateAdapter
import co.delric.voicture.ui.fragments.DisplaySavedProjectsFragment
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.activity_display_projects.*
import javax.inject.Inject

interface DisplayProjectsView {
    fun startProjectCreationFlow()
    fun showProjectNameNeeded()
    fun showProjectNameTaken()
    fun sendChoosePhotosIntent()
}

class DisplayProjectsActivity : AppCompatActivity(),
    DisplaySavedProjectsFragment.VoictureListProvider,
    VoictureProjectDelegateAdapter.OnViewSelectedListener,
    DisplayProjectsView {

    companion object {
        const val PICK_IMAGES = 1
    }

    @Inject
    protected lateinit var displayProjectsPresenter: DisplayProjectsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_display_projects)
        setSupportActionBar(toolbar)

        VoictureApplication.applicationComponent
            .activityComponent()
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)

        createProjectFab.setOnClickListener { displayProjectsPresenter.onNewProjectClicked(this) }
        if (savedInstanceState == null) changeFragment(DisplaySavedProjectsFragment(), false)
    }

    override fun getVoictureProjectList() = displayProjectsPresenter.getSavedProjects()

    override fun onItemSelected(project: VoictureProject) =
        startActivity(Intents.createProjectIntent(displayProjectsPresenter.getJsonForProject(project), this))

    override fun startProjectCreationFlow() {
        val inputEditText = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_TEXT
            setText(getString(
                R.string.default_project_name, displayProjectsPresenter.getSavedProjects().size.toString()))
        }

        AlertDialog.Builder(this).apply {
            setTitle(R.string.choose_project_name)
            setView(inputEditText)
            setPositiveButton("Create") { _, _ ->
                displayProjectsPresenter.onCreateProject(inputEditText.text.toString(), this@DisplayProjectsActivity)
            }
            setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            show()
        }
    }

    override fun showProjectNameNeeded() {
        Toast.makeText(applicationContext, "Need a project name first!", Toast.LENGTH_LONG).show()
    }

    override fun showProjectNameTaken() {
        Toast.makeText(applicationContext, "Project name is already taken", Toast.LENGTH_LONG).show()
    }

    override fun sendChoosePhotosIntent() {
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

        displayProjectsPresenter.createProjectFromUris(this, selectedImageUriList.toList())
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
