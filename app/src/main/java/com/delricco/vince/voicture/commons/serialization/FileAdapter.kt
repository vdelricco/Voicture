package com.delricco.vince.voicture.commons.serialization

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.io.File

class FileAdapter {
    @FromJson
    fun fromJson(filePath: String): File {
        return File(filePath)
    }

    @ToJson
    fun toJson(file: File): String {
        return file.absolutePath
    }
}