package com.delricco.vince.voicture.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.VoictureApplication
import com.delricco.vince.voicture.commons.serialization.VoictureProjectSerDes
import com.delricco.vince.voicture.commons.sharedprefs.SavedProject
import com.delricco.vince.voicture.intents.IntentKeys
import com.delricco.vince.voicture.intents.Intents
import com.delricco.vince.voicture.models.Voicture
import com.delricco.vince.voicture.models.VoictureProject
import com.delricco.vince.voicture.ui.fragments.CreateProjectFragment
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CreateProjectFragment.CreateProjectListener {
    companion object {
        val PICK_IMAGES = 1
    }

    @Inject protected lateinit var savedProjectPrefs: SavedProject
    @Inject protected lateinit var voictureProjectSerDes: VoictureProjectSerDes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VoictureApplication.activityComponent.inject(this)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            changeFragment(CreateProjectFragment(), false)
        }
    }

    override fun onProjectCreateClicked() = createProject()

    private fun createProject() {
        startActivityForResult(Intent.createChooser(Intents.CHOOSE_MULTIPLE_PHOTOS, "Select Pictures"), PICK_IMAGES)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGES && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val selectedImageUriList = ArrayList<Uri>()
            when {
                data.data != null -> {
                    Timber.d { "Adding ${data.data} to selected image uri list" }
                    selectedImageUriList.add(data.data)
                }
                data.clipData != null -> (0..(data.clipData.itemCount - 1)).mapTo(selectedImageUriList) {
                    Timber.d { "Adding ${data.clipData.getItemAt(it).uri} to selected image uri list" }
                    data.clipData.getItemAt(it).uri
                }
                else -> return
            }

            startActivity(Intents.createProjectIntent(
                    voictureProjectSerDes.toJson(
                            VoictureProject(selectedImageUriList.map { Voicture(it) }, "Temp")), this))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_new_project -> {
                createProject()
                return true
            }
            R.id.action_show_saved -> {
                if (savedProjectPrefs.hasSavedProject()) {
                    val intent = Intent(applicationContext, PreviewVoictureProjectActivity::class.java)
                    intent.putExtra(IntentKeys.VOICTURE_PROJECT, voictureProjectSerDes.toJson(savedProjectPrefs.getSavedProject()))
                    startActivity(intent)
                } else {
                    Toast.makeText(this, getString(R.string.no_saved_project), Toast.LENGTH_LONG).show()
                }
                return true
            }
            R.id.action_edit_saved -> {
                if (savedProjectPrefs.hasSavedProject()) {
                    startActivity(Intents.createProjectIntent(voictureProjectSerDes.toJson(savedProjectPrefs.getSavedProject()), this))
                } else {
                    Toast.makeText(this, getString(R.string.no_saved_project), Toast.LENGTH_LONG).show()
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeFragment(f: Fragment, addToBackStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, f)
            if (addToBackStack) {
                addToBackStack(null)
            }
        }.commit()
    }
}
