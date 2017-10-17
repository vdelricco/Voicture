package com.delricco.vince.voicture.audio

import android.media.MediaPlayer
import java.io.File

class AudioPlaybackManager {
    private val mediaPlayer by lazy { MediaPlayer() }

    fun playAudio(audioFile: File) {
        mediaPlayer.apply {
            reset()
            setDataSource(audioFile.absolutePath)
            prepare()
            start()
        }
    }

    fun stop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }
}
