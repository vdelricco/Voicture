package com.delricco.vince.voicture.activities

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.audio.AudioRecordingManager
import com.delricco.vince.voicture.models.Voicture
import com.delricco.vince.voicture.ui.adapters.ImageViewerAdapter
import kotlinx.android.synthetic.main.activity_project_creation.*
import java.io.File

class ProjectCreationActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    private val selectedImageUriList : ArrayList<Uri> by lazy { getSelectedImageUriListFromIntent() }
    private val audioRecordingManager : AudioRecordingManager by lazy { AudioRecordingManager() }
    private val voictureList = ArrayList<Voicture>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_creation)
        imageViewer.adapter = ImageViewerAdapter(supportFragmentManager, selectedImageUriList)
        imageViewer.addOnPageChangeListener(this)
        indicator.setViewPager(imageViewer)
        recordingOnOff.setOnClickListener { onRecordButtonClicked() }
        playAudio.setOnClickListener { onPlayAudioButtonClicked() }
        selectedImageUriList.mapTo(voictureList) { Voicture(it) }
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

    private fun onPlayAudioButtonClicked() = MediaPlayer().apply {
        setDataSource(currentVoicture().getAudioFile()?.absolutePath)
        prepare()
        start()
    }

    private fun currentVoicture() = voictureList[imageViewer.currentItem]

    private fun getSelectedImageUriListFromIntent() : ArrayList<Uri> {
        if (intent.extras == null || !intent.extras.containsKey("SelectedImageUriList")) {
            throw IllegalArgumentException("Must send SelectedImageUriList to ProjectCreationActivity")
        } else if ((intent.extras.get("SelectedImageUriList") as ArrayList<Uri>).isEmpty()) {
            throw IllegalArgumentException("Image Uri list must contain at least one Uri")
        } else {
            return intent.extras.get("SelectedImageUriList") as ArrayList<Uri>
        }
    }
}
