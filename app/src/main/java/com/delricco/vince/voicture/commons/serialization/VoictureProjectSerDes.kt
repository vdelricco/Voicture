package com.delricco.vince.voicture.commons.serialization

import com.delricco.vince.voicture.models.VoictureProject
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

class VoictureProjectSerDes {
    companion object {
        private val voictureProjectJsonAdapter = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(FileAdapter())
                .add(UriAdapter())
                .build()
                .adapter(VoictureProject::class.java)

        fun toJson(project: VoictureProject): String {
            return voictureProjectJsonAdapter.toJson(project)
        }

        fun fromJson(json: String): VoictureProject {
            return voictureProjectJsonAdapter.fromJson(json)!!
        }
    }
}
