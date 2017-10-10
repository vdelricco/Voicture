package com.delricco.vince.voicture.intents

import com.delricco.vince.voicture.BuildConfig

class IntentKeys {
    companion object {
        private val baseIntentUri = BuildConfig.APPLICATION_ID + "."
        val SELECTED_IMAGE_URI_LIST = baseIntentUri + "SelectedImageUriList"
        val VOICTURE_IMAGE_URI_LIST = baseIntentUri + "VoictureImageUriList"
        val VOICTURE_AUDIO_FILE_LIST = baseIntentUri + "VoictureAudioFileList"
    }
}