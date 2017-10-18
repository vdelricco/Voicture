package com.delricco.vince.voicture.intents

import com.delricco.vince.voicture.BuildConfig

class IntentKeys {
    companion object {
        private val baseIntentUri = BuildConfig.APPLICATION_ID + ".intents.IntentKeys."
        val VOICTURE_PROJECT = baseIntentUri + "VoictureProject"
    }
}
