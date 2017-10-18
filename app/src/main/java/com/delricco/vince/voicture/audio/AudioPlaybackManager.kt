package com.delricco.vince.voicture.audio

import android.media.MediaPlayer
import java.io.File

class AudioPlaybackManager {
    private val emptyListener = MediaPlayer.OnCompletionListener {}
    private val mediaPlayer by lazy { MediaPlayer() }

    fun playAudio(audioFile: File, onCompletionListener: MediaPlayer.OnCompletionListener = emptyListener) {
        mediaPlayer.apply {
            reset()
            setDataSource(audioFile.absolutePath)
            prepare()
            setOnCompletionListener(onCompletionListener)
            start()
        }
    }

    fun stop() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }

    fun isPlaying() = mediaPlayer.isPlaying
}
