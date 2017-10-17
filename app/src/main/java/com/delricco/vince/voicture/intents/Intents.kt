package com.delricco.vince.voicture.intents

import android.content.Intent

class Intents {
    companion object {
        val CHOOSE_MULTIPLE_PHOTOS: Intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_OPEN_DOCUMENT
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
    }
}
