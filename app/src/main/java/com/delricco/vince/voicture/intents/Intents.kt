package com.delricco.vince.voicture.intents

import android.content.Context
import android.content.Intent
import com.delricco.vince.voicture.activities.ProjectCreationActivity

class Intents {
    companion object {
        val CHOOSE_MULTIPLE_PHOTOS: Intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_OPEN_DOCUMENT
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }

        fun createProjectIntent(voictureProjectJson: String, context: Context): Intent {
            return Intent(context, ProjectCreationActivity::class.java).putExtra(IntentKeys.VOICTURE_PROJECT, voictureProjectJson)
        }
    }
}
