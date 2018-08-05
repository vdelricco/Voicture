package com.delricco.vince.voicture.intents

import android.content.Context
import android.content.Intent
import com.delricco.vince.voicture.activities.EditProjectActivity
import com.delricco.vince.voicture.intents.IntentKeys.Companion.VOICTURE_PROJECT

class Intents {
    companion object {
        val CHOOSE_MULTIPLE_PHOTOS: Intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_OPEN_DOCUMENT
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }

        fun createProjectIntent(voictureProjectJson: String, context: Context): Intent =
                Intent(context, EditProjectActivity::class.java).putExtra(VOICTURE_PROJECT, voictureProjectJson)
    }
}