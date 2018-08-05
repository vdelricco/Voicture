package com.delricco.vince.voicture.commons.serialization

import android.net.Uri
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class UriAdapter {
    @FromJson
    fun fromJson(uri: String): Uri {
        return Uri.parse(uri)
    }

    @ToJson
    fun toJson(uri: Uri): String {
        return uri.toString()
    }
}