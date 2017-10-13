package com.delricco.vince.voicture.intents

import android.content.Intent

class Intents {
    companion object {
        val CHOOSE_MULTIPLE_PHOTOS = object: Intent() {
            init {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
        }
    }
}
