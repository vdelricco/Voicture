package com.delricco.vince.voicture.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.activities.ProjectCreationActivity
import com.delricco.vince.voicture.intents.IntentKeys.Companion.SELECTED_IMAGE_URI_LIST
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.fragment_create_project.*

class CreateProjectFragment : Fragment() {
    companion object {
        val PICK_IMAGES = 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_project, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        createNewProjectCard.setOnClickListener { startActivityForResult(Intent.createChooser(getPickImagesIntent(), "Select Picture"), PICK_IMAGES) }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGES && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val selectedImageUriList = ArrayList<Uri>()
            if (data.data != null) {
                Timber.d { "Adding ${data.data} to selected image uri list" }
                selectedImageUriList.add(data.data)
            } else {
                (0 .. (data.clipData.itemCount - 1)).mapTo(selectedImageUriList) {
                    Timber.d { "Adding ${data.clipData.getItemAt(it).uri} to selected image uri list" }
                    data.clipData.getItemAt(it).uri
                }
            }

            val createProjectIntent = Intent(activity, ProjectCreationActivity::class.java)
            createProjectIntent.putParcelableArrayListExtra(SELECTED_IMAGE_URI_LIST, selectedImageUriList)
            activity.startActivity(createProjectIntent)
        }
    }

    private fun getPickImagesIntent() : Intent {
        val pickImagesIntent = Intent()
        pickImagesIntent.apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        return pickImagesIntent
    }
}
