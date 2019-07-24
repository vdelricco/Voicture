package co.delric.voicture.commons.serialization

import co.delric.voicture.di.components.ApplicationScope
import co.delric.voicture.models.VoictureProject
import com.squareup.moshi.JsonAdapter
import javax.inject.Inject

@ApplicationScope
class VoictureProjectSerDes @Inject constructor(
    private val voictureProjectJsonAdapter: JsonAdapter<VoictureProject>,
    private val voictureProjectListJsonAdapter: JsonAdapter<List<VoictureProject>>
) {
    fun toJson(project: VoictureProject): String = voictureProjectJsonAdapter.toJson(project)
    fun fromJson(json: String): VoictureProject = voictureProjectJsonAdapter.fromJson(json)!!

    fun listToJson(projectList: List<VoictureProject>): String =
        voictureProjectListJsonAdapter.toJson(projectList)

    fun listFromJson(json: String): List<VoictureProject> =
        voictureProjectListJsonAdapter.fromJson(json)!!
}