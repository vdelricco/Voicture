package com.delricco.vince.voicture.audio

import android.media.MediaPlayer
import io.reactivex.Completable
import java.io.File

class AudioPlaybackManager {
    private var mediaPlayer = MediaPlayer()

    fun playAudio(audioFile: File): Completable {
        return Completable.create({ subscriber ->
            mediaPlayer.apply {
                reset()
                setDataSource(audioFile.absolutePath)
                prepare()
                setOnCompletionListener { subscriber.onComplete() }
                start()
            }
        })
    }

    fun stop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()
    }

    fun isPlaying() = mediaPlayer.isPlaying
}
