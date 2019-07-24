package co.delric.voicture.di.modules

import co.delric.voicture.commons.serialization.FileAdapter
import co.delric.voicture.commons.serialization.UriAdapter
import co.delric.voicture.di.components.ApplicationScope
import co.delric.voicture.models.VoictureProject
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Module
import dagger.Provides

@Module
open class JsonModule {
    @Provides
    fun provideKotlinJsonAdapterFactory() = KotlinJsonAdapterFactory()

    @Provides
    fun provideMoshi(
        kotlinJsonAdapterFactory: KotlinJsonAdapterFactory,
        fileAdapter: FileAdapter,
        uriAdapter: UriAdapter
    ): Moshi = Moshi.Builder()
        .add(kotlinJsonAdapterFactory)
        .add(fileAdapter)
        .add(uriAdapter)
        .build()

    @Provides
    @ApplicationScope
    fun provideVoictureProjectJsonAdapter(moshi: Moshi): JsonAdapter<VoictureProject> =
        moshi.adapter(VoictureProject::class.java)

    @Provides
    @ApplicationScope
    fun provideVoictureProjectListJsonAdapter(moshi: Moshi): JsonAdapter<List<VoictureProject>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, VoictureProject::class.java))
}