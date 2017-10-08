package com.delricco.vince.voicture.activities

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.ui.adapters.ImageViewerAdapter
import kotlinx.android.synthetic.main.activity_project_creation.*

class ProjectCreationActivity : AppCompatActivity() {
    private val selectedImageUriList : ArrayList<Uri> by lazy { getSelectedImageUriListFromIntent() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_creation)
        imageViewer.adapter = ImageViewerAdapter(supportFragmentManager, selectedImageUriList)
        indicator.setViewPager(imageViewer)
    }

    private fun getSelectedImageUriListFromIntent() : ArrayList<Uri> {
        if (intent.extras == null || !intent.extras.containsKey("SelectedImageUriList")) {
            throw IllegalArgumentException("Must send SelectedImageUriList to ProjectCreationActivity")
        } else if ((intent.extras.get("SelectedImageUriList") as ArrayList<Uri>).isEmpty()) {
            throw IllegalArgumentException("Image Uri list must contain at least one Uri")
        } else {
            return intent.extras.get("SelectedImageUriList") as ArrayList<Uri>
        }
    }
}
