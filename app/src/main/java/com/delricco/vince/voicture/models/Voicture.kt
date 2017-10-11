package com.delricco.vince.voicture.models

import android.net.Uri
import java.io.File

data class Voicture(val imageUri: Uri, var audioFile: File? = null)
