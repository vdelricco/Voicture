package co.delric.voicture.commons.serialization

import co.delric.voicture.di.components.ApplicationScope
import co.delric.voicture.models.VoictureProject
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ApplicationScope
class VoictureProjectSerDes @Inject constructor() {
    // TODO : Inject this
    private val voictureProjectJsonAdapter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(FileAdapter())
        .add(UriAdapter())
        .build()
        .adapter(VoictureProject::class.java)

    // TODO: Inject this
    private val voictureProjectListJsonAdapter: JsonAdapter<List<VoictureProject>> = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(FileAdapter())
        .add(UriAdapter())
        .build()
        .adapter(Types.newParameterizedType(List::class.java, VoictureProject::class.java))

    fun toJson(project: VoictureProject): String {
        return voictureProjectJsonAdapter.toJson(project)
    }

    fun fromJson(json: String): VoictureProject {
        return voictureProjectJsonAdapter.fromJson(json)!!
    }

    fun listToJson(projectList: List<VoictureProject>): String {
        return voictureProjectListJsonAdapter.toJson(projectList)
    }

    fun listFromJson(json: String): List<VoictureProject> {
        return voictureProjectListJsonAdapter.fromJson(json)!!
    }
}