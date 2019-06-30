package co.delric.voicture.models

import co.delric.voicture.ui.adapters.AdapterConstants
import co.delric.voicture.ui.adapters.ViewType

data class VoictureProject(var data: List<Voicture>, val name: String) : ViewType {
    val imageUriList get() = data.map { it.imageUri }
    val audioFileList get() = data.map { it.audioFile }
    override fun getViewType() = AdapterConstants.PROJECT
}