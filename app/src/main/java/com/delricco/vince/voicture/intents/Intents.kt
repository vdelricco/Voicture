package com.delricco.vince.voicture.intents

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.delricco.vince.voicture.activities.ProjectCreationActivity

class Intents {
    companion object {
        val CHOOSE_MULTIPLE_PHOTOS: Intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_OPEN_DOCUMENT
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }

        fun createProjectIntent(selectedImageUriList: ArrayList<Uri>, context: Context): Intent {
            return Intent(context, ProjectCreationActivity::class.java).putParcelableArrayListExtra(IntentKeys.SELECTED_IMAGE_URI_LIST, selectedImageUriList)
        }
    }
}
