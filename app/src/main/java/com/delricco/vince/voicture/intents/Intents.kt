package com.delricco.vince.voicture.intents

import android.content.Intent

class Intents {
    companion object {
        fun chooseMultiplePhotos(): Intent {
            val intent = Intent()
            intent.apply {
                type = "image/*"
                action = Intent.ACTION_OPEN_DOCUMENT
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            return intent
        }
    }
}
