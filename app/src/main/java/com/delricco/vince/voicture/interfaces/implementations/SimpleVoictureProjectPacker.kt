package com.delricco.vince.voicture.interfaces.implementations

import android.content.Intent
import android.net.Uri
import com.delricco.vince.voicture.intents.IntentKeys
import com.delricco.vince.voicture.interfaces.VoictureProjectPacker
import com.delricco.vince.voicture.models.Voicture
import java.io.File

class SimpleVoictureProjectPacker : VoictureProjectPacker {
    override fun getPackedIntent(voictureArrayList: ArrayList<Voicture>): Intent {
        val intent = Intent()
        val imageUriList = ArrayList<Uri>()
        val audioFileList = ArrayList<File?>()
        voictureArrayList.forEach {
            imageUriList.add(it.imageUri)
            audioFileList.add(it.audioFile)
        }
        intent.putParcelableArrayListExtra(IntentKeys.VOICTURE_IMAGE_URI_LIST, imageUriList)
        intent.putExtra(IntentKeys.VOICTURE_AUDIO_FILE_LIST, audioFileList)
        return intent
    }
}
