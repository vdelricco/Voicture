package co.delric.voicture.commons.serialization

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.io.File
import javax.inject.Inject

class FileAdapter @Inject constructor() {
    @FromJson fun fromJson(filePath: String) = File(filePath)
    @ToJson fun toJson(file: File) = file.absolutePath
}