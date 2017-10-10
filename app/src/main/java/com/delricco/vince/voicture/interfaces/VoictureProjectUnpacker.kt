package com.delricco.vince.voicture.interfaces

import android.content.Intent
import com.delricco.vince.voicture.models.Voicture

interface VoictureProjectUnpacker {
    fun unpackFromIntent(intent: Intent): ArrayList<Voicture>
}
