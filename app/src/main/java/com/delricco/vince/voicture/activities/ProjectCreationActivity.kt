package com.delricco.vince.voicture.activities

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.audio.AudioRecordingManager
import com.delricco.vince.voicture.intents.IntentKeys
import com.delricco.vince.voicture.interfaces.implementations.SimpleVoictureProjectPacker
import com.delricco.vince.voicture.models.Voicture
import com.delricco.vince.voicture.ui.adapters.ImageViewerAdapter
import kotlinx.android.synthetic.main.activity_project_creation.*
import java.io.File

class ProjectCreationActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    private val selectedImageUriList by lazy { getSelectedImageUriListFromIntent() }
    private val audioRecordingManager by lazy { AudioRecordingManager() }
    private val voictureProjectPacker by lazy { SimpleVoictureProjectPacker() }
    private val mediaPlayer by lazy { MediaPlayer() }
    private val voictureProject = ArrayList<Voicture>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_creation)
        imageViewer.adapter = ImageViewerAdapter(supportFragmentManager, selectedImageUriList)
        imageViewer.addOnPageChangeListener(this)
        indicator.setViewPager(imageViewer)
        recordingOnOff.setOnClickListener { onRecordButtonClicked() }
        playAudio.setOnClickListener { onPlayAudioButtonClicked() }
        previewVoictureProject.setOnClickListener { onPreviewVoictureProjectClicked() }
        selectedImageUriList.mapTo(voictureProject) { Voicture(it) }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (currentVoicture().hasAudio()) {
            playAudio.visibility = View.VISIBLE
        } else {
            playAudio.visibility = View.GONE
        }
    }

    override fun onPageSelected(position: Int) {
    }

    private fun onRecordButtonClicked() =
            if (audioRecordingManager.getState() == AudioRecordingManager.RecordingState.STOPPED) {
                val audioFile = File(filesDir.absolutePath + "${File.separator}${System.currentTimeMillis()}.mp4")
                if (currentVoicture().hasAudio()) {
                    currentVoicture().getAudioFile()?.delete()
                }
                currentVoicture().setAudioFile(audioFile)
                audioRecordingManager.startRecording(audioFile)
                recordingOnOff.setImageResource(android.R.drawable.ic_media_pause)
                imageViewer.setPagingEnabled(false)
            } else {
                audioRecordingManager.stopRecording()
                recordingOnOff.setImageResource(android.R.drawable.ic_btn_speak_now)
                imageViewer.setPagingEnabled(true)
                playAudio.visibility = View.VISIBLE
            }

    private fun onPlayAudioButtonClicked() = mediaPlayer.apply {
        reset()
        setDataSource(currentVoicture().getAudioFile()?.absolutePath)
        prepare()
        start()
    }

    private fun onPreviewVoictureProjectClicked() {
        startActivity(voictureProjectPacker
                .getPackedIntent(voictureProject)
                .setClass(applicationContext, PreviewVoictureProjectActivity::class.java))
    }

    private fun currentVoicture() = voictureProject[imageViewer.currentItem]

    private fun getSelectedImageUriListFromIntent(): ArrayList<Uri> {
        if (intent.extras == null || !intent.extras.containsKey(IntentKeys.SELECTED_IMAGE_URI_LIST)) {
            throw IllegalArgumentException("Must send SelectedImageUriList to ProjectCreationActivity")
        } else if ((intent.extras.getParcelableArrayList<Uri>(IntentKeys.SELECTED_IMAGE_URI_LIST)).isEmpty()) {
            throw IllegalArgumentException("Image Uri list must contain at least one Uri")
        } else {
            return intent.extras.getParcelableArrayList<Uri>(IntentKeys.SELECTED_IMAGE_URI_LIST)
        }
    }
}
