package com.delricco.vince.voicture.activities

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.VoictureApplication
import com.delricco.vince.voicture.audio.AudioPlaybackManager
import com.delricco.vince.voicture.interfaces.implementations.SimpleVoictureProjectUnpacker
import com.delricco.vince.voicture.models.Voicture
import com.delricco.vince.voicture.ui.adapters.ImageViewerAdapter
import kotlinx.android.synthetic.main.activity_preview_voicture.*
import javax.inject.Inject

class PreviewVoictureProjectActivity : AppCompatActivity() {
    @Inject protected lateinit var audioPlaybackManager: AudioPlaybackManager

    private val handler by lazy { Handler() }
    private val voictureProjectUnpacker by lazy { SimpleVoictureProjectUnpacker() }
    private lateinit var voictureProject: ArrayList<Voicture>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_voicture)
        VoictureApplication.audioComponent.inject(this)
        voictureProject = voictureProjectUnpacker.unpackFromIntent(intent)
        imageViewer.adapter = ImageViewerAdapter(supportFragmentManager, voictureProject.map { it.imageUri } as ArrayList<Uri>)
        imageViewer.setPagingEnabled(false)
        startVoictureProjectPlayback()
    }

    override fun onPause() {
        handler.removeCallbacksAndMessages(null)
        super.onPause()
    }

    private fun startVoictureProjectPlayback() {
        imageViewer.currentItem = 0
        nextVoicture()
    }

    private fun nextVoicture() {
        if (currentVoicture().audioFile != null) {
            audioPlaybackManager.playAudio(currentVoicture().audioFile!!)
        }
        // TODO: Obvious
        val arbitraryTime = 5000L
        if (imageViewer.currentItem == voictureProject.size - 1) {
            handler.postDelayed({ finish() }, arbitraryTime)
        } else {
            handler.postDelayed({
                imageViewer.currentItem++
                nextVoicture()
            }, arbitraryTime)
        }
    }

    private fun currentVoicture() = voictureProject[imageViewer.currentItem]
}
