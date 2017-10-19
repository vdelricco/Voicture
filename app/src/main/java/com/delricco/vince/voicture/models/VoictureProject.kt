package com.delricco.vince.voicture.models

import android.net.Uri
import java.io.File

data class VoictureProject(var data: List<Voicture>, val name: String) {
    fun getImageUriList(): List<Uri> {
        return data.map { it.imageUri }
    }

    fun getAudioFileList(): List<File> {
        return data.map { it.audioFile }
    }
}
