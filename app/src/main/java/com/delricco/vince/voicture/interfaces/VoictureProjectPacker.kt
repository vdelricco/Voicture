package com.delricco.vince.voicture.interfaces

import android.content.Intent
import com.delricco.vince.voicture.models.Voicture

interface VoictureProjectPacker {
    fun getPackedIntent(voictureArrayList: ArrayList<Voicture>): Intent
}
