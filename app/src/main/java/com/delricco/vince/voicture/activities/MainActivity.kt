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
import com.delricco.vince.voicture.commons.sharedprefs.SavedProject
import com.delricco.vince.voicture.intents.Intents
import com.delricco.vince.voicture.interfaces.implementations.SimpleVoictureProjectPacker
import com.delricco.vince.voicture.ui.fragments.CreateProjectFragment
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CreateProjectFragment.CreateProjectListener {
    companion object {
        val PICK_IMAGES = 1
    }

    @Inject protected lateinit var savedProjectPrefs: SavedProject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VoictureApplication.activityComponent.inject(this)
        setSupportActionBar(toolbar)
        changeFragment(CreateProjectFragment(), false)
    }

    override fun onProjectCreateClicked() {
        createProject()
    }

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

            startActivity(Intents.createProjectIntent(selectedImageUriList, this))
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
                    startActivity(SimpleVoictureProjectPacker()
                            .getPackedIntent(ArrayList(savedProjectPrefs.getSavedProject().data))
                            .setClass(applicationContext, PreviewVoictureProjectActivity::class.java))
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
