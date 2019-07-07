package co.delric.voicture.intents

import android.content.Context
import android.content.Intent
import co.delric.voicture.ui.activities.EditProjectActivity
import co.delric.voicture.intents.IntentKeys.Companion.VOICTURE_PROJECT

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