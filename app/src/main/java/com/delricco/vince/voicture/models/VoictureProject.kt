package com.delricco.vince.voicture.models

import com.delricco.vince.voicture.ui.adapters.AdapterConstants
import com.delricco.vince.voicture.ui.adapters.ViewType

data class VoictureProject(var data: List<Voicture>, val name: String) : ViewType {
    fun getImageUriList() = data.map { it.imageUri }
    fun getAudioFileList() = data.map { it.audioFile }
    override fun getViewType() = AdapterConstants.PROJECT
}