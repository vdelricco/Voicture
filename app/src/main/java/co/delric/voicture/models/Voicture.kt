package co.delric.voicture.models

import android.net.Uri
import java.io.File

data class Voicture(val imageUri: Uri, val audioFile: File) {
    val hasAudio = audioFile.length() > 0L
}