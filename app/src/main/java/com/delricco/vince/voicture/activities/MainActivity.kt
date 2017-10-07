package com.delricco.vince.voicture.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.delricco.vince.voicture.R
import com.github.ajalt.timberkt.Timber
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*


class MainActivity : AppCompatActivity() {
    companion object {
        val PICK_IMAGES = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { _ ->
            startActivityForResult(Intent.createChooser(getPickImagesIntent(), "Select Picture"), PICK_IMAGES)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            val photoUri : Uri = if (data.data != null) {
                Timber.d { "Loading ${data.data} with Picasso" }
                data.data
            } else {
                Timber.d { "Loading first photo of multiple: ${data.clipData.getItemAt(0).uri} with Picasso" }
                data.clipData.getItemAt(0).uri
            }
            Picasso.with(applicationContext)
                    .load(photoUri)
                    .into(selectedPhoto)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getPickImagesIntent() : Intent {
        val pickImagesIntent = Intent()
        pickImagesIntent.type = "image/*"
        pickImagesIntent.action = Intent.ACTION_GET_CONTENT
        pickImagesIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        return pickImagesIntent
    }
}
