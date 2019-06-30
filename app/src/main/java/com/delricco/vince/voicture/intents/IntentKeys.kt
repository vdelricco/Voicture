package com.delricco.vince.voicture.intents

import com.delricco.vince.voicture.BuildConfig

class IntentKeys {
    companion object {
        private const val baseIntentUri = BuildConfig.APPLICATION_ID + ".intents.IntentKeys."
        const val VOICTURE_PROJECT = baseIntentUri + "VoictureProject"
    }
}