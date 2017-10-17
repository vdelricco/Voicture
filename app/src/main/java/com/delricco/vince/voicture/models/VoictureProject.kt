package com.delricco.vince.voicture.models

import android.net.Uri

data class VoictureProject(var data: List<Voicture>, val name: String) {
    fun getImageUriList(): List<Uri> {
        return data.map { it.imageUri }
    }
}
