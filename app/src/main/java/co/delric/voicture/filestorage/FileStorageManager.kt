package co.delric.voicture.filestorage

import android.content.Context
import co.delric.voicture.di.components.ApplicationScope
import com.github.ajalt.timberkt.Timber
import io.reactivex.Completable
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

@ApplicationScope
class FileStorageManager @Inject constructor(val context: Context) {
    private val tempAudioFileList = ArrayList<File>()

    fun createTempProjectAudioFiles(numFiles: Int): Observable<File> {
        return Observable.create { subscriber ->
            repeat(numFiles) {
                Thread.sleep(1L) // yuck

                val tempAudioFile = File(context.filesDir.absolutePath + "${File.separator}${System.currentTimeMillis()}.mp4")

                if (tempAudioFile.createNewFile()) {
                    Timber.d { "Created ${tempAudioFile.absolutePath}" }
                    tempAudioFileList.add(tempAudioFile)
                    subscriber.onNext(tempAudioFile)
                } else {
                    subscriber.onError(Exception("File creation for ${tempAudioFile.absolutePath} failed"))
                }
            }
            subscriber.onComplete()
        }
    }

    fun clearTempFiles(): Completable {
        return Completable.create { subscriber ->
            tempAudioFileList.filterNot {
                Timber.d { "Deleting ${it.absolutePath}" }
                it.delete()
            }.forEach {
                subscriber.onError(Exception("Failed to delete ${it.absolutePath}"))
            }
            tempAudioFileList.clear()
            subscriber.onComplete()
        }
    }

    fun removeFilesFromTempList(fileList: List<File>) {
        tempAudioFileList.removeAll(fileList)
    }
}