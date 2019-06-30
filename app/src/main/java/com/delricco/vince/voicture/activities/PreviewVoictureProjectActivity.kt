package com.delricco.vince.voicture.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.VoictureApplication
import com.delricco.vince.voicture.audio.AudioPlaybackManager
import com.delricco.vince.voicture.commons.serialization.VoictureProjectSerDes
import com.delricco.vince.voicture.intents.IntentKeys
import com.delricco.vince.voicture.models.VoictureProject
import com.delricco.vince.voicture.ui.adapters.ImageViewerAdapter
import kotlinx.android.synthetic.main.activity_preview_voicture.*
import javax.inject.Inject

class PreviewVoictureProjectActivity : AppCompatActivity() {
    @Inject protected lateinit var audioPlaybackManager: AudioPlaybackManager
    @Inject protected lateinit var voictureProjectSerDes: VoictureProjectSerDes

    private val handler by lazy { Handler() }
    private lateinit var voictureProject: VoictureProject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_preview_voicture)

        VoictureApplication.activityComponent.inject(this)

        voictureProject = voictureProjectSerDes.fromJson(intent.getStringExtra(IntentKeys.VOICTURE_PROJECT))
        imageViewer.adapter = ImageViewerAdapter(supportFragmentManager, voictureProject.imageUriList)
        imageViewer.setPagingEnabled(false)

        startVoictureProjectPlayback()
    }

    override fun onPause() {
        handler.removeCallbacksAndMessages(null)
        audioPlaybackManager.stop()
        super.onPause()
    }

    private fun startVoictureProjectPlayback() {
        imageViewer.currentItem = 0
        nextVoicture()
    }

    private fun nextVoicture() {
        if (currentVoicture().hasAudio) {
            audioPlaybackManager.playAudio(currentVoicture().audioFile).subscribe()
        }
        // TODO: Obvious
        val arbitraryTime = 5000L
        if (imageViewer.currentItem == voictureProject.data.size - 1) {
            handler.postDelayed({ finish() }, arbitraryTime)
        } else {
            handler.postDelayed({
                imageViewer.currentItem++
                nextVoicture()
            }, arbitraryTime)
        }
    }

    private fun currentVoicture() = voictureProject.data[imageViewer.currentItem]
}
