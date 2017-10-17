package com.delricco.vince.voicture.intents

import com.delricco.vince.voicture.BuildConfig

class IntentKeys {
    companion object {
        private val baseIntentUri = BuildConfig.APPLICATION_ID + ".intents.IntentKeys."
        val SELECTED_IMAGE_URI_LIST = baseIntentUri + "SelectedImageUriList"
        val VOICTURE_PROJECT = baseIntentUri + "VoictureProject"
    }
}
