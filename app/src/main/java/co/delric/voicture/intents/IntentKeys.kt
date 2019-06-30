package co.delric.voicture.intents

import co.delric.voicture.BuildConfig

class IntentKeys {
    companion object {
        private const val baseIntentUri = BuildConfig.APPLICATION_ID + ".intents.IntentKeys."
        const val VOICTURE_PROJECT = baseIntentUri + "VoictureProject"
    }
}