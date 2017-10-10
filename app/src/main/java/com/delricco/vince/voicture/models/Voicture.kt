package com.delricco.vince.voicture.models

import android.net.Uri
import java.io.File

class Voicture(imageUri: Uri) {
    private val imageUri = imageUri
    private var audioFile: File? = null

    fun getImageUri() : Uri = imageUri
    fun hasAudio() : Boolean = audioFile != null
    fun setAudioFile(file : File?) { audioFile = file }
    fun getAudioFile() : File? = audioFile
}
