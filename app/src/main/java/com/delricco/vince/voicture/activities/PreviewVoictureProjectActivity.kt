package com.delricco.vince.voicture.activities

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.interfaces.implementations.SimpleVoictureProjectUnpacker
import com.delricco.vince.voicture.models.Voicture
import com.delricco.vince.voicture.ui.adapters.ImageViewerAdapter
import kotlinx.android.synthetic.main.activity_preview_voicture.*

class PreviewVoictureProjectActivity : AppCompatActivity() {
    private val handler by lazy { Handler() }
    private val mediaPlayer by lazy { MediaPlayer() }
    private val voictureProjectUnpacker by lazy { SimpleVoictureProjectUnpacker() }
    private lateinit var voictureProject: ArrayList<Voicture>
    private var finishedPlaybackRunnable = Runnable { finish() }
    private var nextVoictureRunnable = Runnable {
        imageViewer.currentItem++
        nextVoicture()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_voicture)
        voictureProject = voictureProjectUnpacker.unpackFromIntent(intent)
        imageViewer.adapter = ImageViewerAdapter(supportFragmentManager, voictureProject.map { it.getImageUri() } as ArrayList<Uri>)
        imageViewer.setPagingEnabled(false)
        startVoictureProjectPlayback()
    }

    override fun onPause() {
        handler.removeCallbacks(finishedPlaybackRunnable, nextVoictureRunnable)
        super.onPause()
    }

    private fun startVoictureProjectPlayback() {
        imageViewer.currentItem = 0
        nextVoicture()
    }

    private fun nextVoicture() {
        // TODO: Move this MediaPlayer business to AudioPlaybackManager or something similar
        mediaPlayer.reset()
        if (voictureProject[imageViewer.currentItem].getAudioFile() != null) {
            mediaPlayer.apply {
                setDataSource(voictureProject[imageViewer.currentItem].getAudioFile()!!.absolutePath)
                prepare()
                start()
            }
        }
        // TODO: Obvious
        val arbitraryTime = 5000L
        if (imageViewer.currentItem == voictureProject.size - 1) {
            handler.postDelayed(finishedPlaybackRunnable, arbitraryTime)
        } else {
            handler.postDelayed(nextVoictureRunnable, arbitraryTime)
        }
    }
}
