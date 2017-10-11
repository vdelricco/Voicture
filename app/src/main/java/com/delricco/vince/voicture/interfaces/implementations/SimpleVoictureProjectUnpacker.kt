package com.delricco.vince.voicture.interfaces.implementations

import android.content.Intent
import android.net.Uri
import com.delricco.vince.voicture.intents.IntentKeys.Companion.VOICTURE_AUDIO_FILE_LIST
import com.delricco.vince.voicture.intents.IntentKeys.Companion.VOICTURE_IMAGE_URI_LIST
import com.delricco.vince.voicture.interfaces.VoictureProjectUnpacker
import com.delricco.vince.voicture.models.Voicture
import java.io.File

class SimpleVoictureProjectUnpacker : VoictureProjectUnpacker {
    override fun unpackFromIntent(intent: Intent): ArrayList<Voicture> {
        val imageUriList = intent.getParcelableArrayListExtra<Uri>(VOICTURE_IMAGE_URI_LIST)
        val audioFileList = intent.getSerializableExtra(VOICTURE_AUDIO_FILE_LIST) as ArrayList<File?>

        if (imageUriList.isEmpty() || audioFileList.isEmpty()) {
            throw IllegalArgumentException("Audio file list and Image Uri list must not be empty")
        } else if (imageUriList.size != audioFileList.size) {
            throw IllegalArgumentException("Audio file list and Image Uri list must be the same size")
        } else {
            val voictureArrayList = ArrayList<Voicture>()
            imageUriList.forEachIndexed { i, uri ->
                val voicture = Voicture(uri, audioFileList[i])
                voictureArrayList.add(voicture)
            }
            return voictureArrayList
        }
    }
}
