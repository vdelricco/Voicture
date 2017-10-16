package com.delricco.vince.voicture.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.commons.sharedprefs.SavedProject
import com.delricco.vince.voicture.interfaces.implementations.SimpleVoictureProjectPacker
import com.delricco.vince.voicture.ui.fragments.CreateProjectFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        changeFragment(CreateProjectFragment(), false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_show_saved -> {
                if (SavedProject(this).isSavedProject()) {
                    startActivity(SimpleVoictureProjectPacker()
                            .getPackedIntent(ArrayList(SavedProject(this).getSavedProject().data))
                            .setClass(applicationContext, PreviewVoictureProjectActivity::class.java))
                } else {
                    Toast.makeText(this, "No saved project", Toast.LENGTH_LONG).show()
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
