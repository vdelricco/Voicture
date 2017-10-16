package com.delricco.vince.voicture.activities

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.delricco.vince.voicture.R
import com.delricco.vince.voicture.VoictureApplication
import com.delricco.vince.voicture.audio.AudioPlaybackManager
import com.delricco.vince.voicture.audio.AudioRecordingManager
import com.delricco.vince.voicture.commons.sharedprefs.SavedProject
import com.delricco.vince.voicture.intents.IntentKeys
import com.delricco.vince.voicture.interfaces.implementations.SimpleVoictureProjectPacker
import com.delricco.vince.voicture.models.Voicture
import com.delricco.vince.voicture.models.VoictureProject
import com.delricco.vince.voicture.ui.adapters.ImageViewerAdapter
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.activity_project_creation.*
import java.io.File
import javax.inject.Inject

class ProjectCreationActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    companion object {
        val RECORD_AUDIO_PERMISSION_REQUEST_CODE = 1
    }

    @Inject protected lateinit var audioRecordingManager: AudioRecordingManager
    @Inject protected lateinit var audioPlaybackManager: AudioPlaybackManager

    private val selectedImageUriList by lazy { getSelectedImageUriListFromIntent() }
    private val voictureProjectPacker by lazy { SimpleVoictureProjectPacker() }
    private val voictureProject = ArrayList<Voicture>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_creation)
        VoictureApplication.audioComponent.inject(this)
        imageViewer.adapter = ImageViewerAdapter(supportFragmentManager, selectedImageUriList)
        imageViewer.addOnPageChangeListener(this)
        indicator.setViewPager(imageViewer)
        recordingOnOff.setOnClickListener { onRecordButtonClicked() }
        playAudio.setOnClickListener { onPlayAudioButtonClicked() }
        previewVoictureProject.setOnClickListener { onPreviewVoictureProjectClicked() }
        selectedImageUriList.mapTo(voictureProject) { Voicture(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_project_creation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save_project -> {
                saveCurrentProject()
                Toast.makeText(this, "Project saved!", Toast.LENGTH_LONG).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (currentVoicture().audioFile != null) {
            playAudio.visibility = View.VISIBLE
        } else {
            playAudio.visibility = View.GONE
        }
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == RECORD_AUDIO_PERMISSION_REQUEST_CODE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onRecordButtonClicked()
            }
        }
    }

    private fun saveCurrentProject() {
        SavedProject(this).saveProject(VoictureProject(voictureProject, "Test"))
    }

    private fun onRecordButtonClicked() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            if (audioRecordingManager.getState() == AudioRecordingManager.RecordingState.STOPPED) {
                val audioFile = File(filesDir.absolutePath + "${File.separator}${System.currentTimeMillis()}.mp4")
                if (currentVoicture().audioFile != null) {
                    currentVoicture().audioFile!!.delete()
                }
                currentVoicture().audioFile = audioFile
                audioRecordingManager.startRecording(audioFile)
                recordingOnOff.setImageResource(android.R.drawable.ic_media_pause)
                imageViewer.setPagingEnabled(false)
            } else {
                audioRecordingManager.stopRecording()
                recordingOnOff.setImageResource(android.R.drawable.ic_btn_speak_now)
                imageViewer.setPagingEnabled(true)
                playAudio.visibility = View.VISIBLE
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_AUDIO_PERMISSION_REQUEST_CODE)
        }
    }

    private fun onPlayAudioButtonClicked() = audioPlaybackManager.playAudio(currentVoicture().audioFile!!)

    private fun onPreviewVoictureProjectClicked() {
        startActivity(voictureProjectPacker
                .getPackedIntent(voictureProject)
                .setClass(applicationContext, PreviewVoictureProjectActivity::class.java))
    }

    private fun currentVoicture() = voictureProject[imageViewer.currentItem]

    private fun getSelectedImageUriListFromIntent(): ArrayList<Uri> {
        if (intent.extras == null || !intent.extras.containsKey(IntentKeys.SELECTED_IMAGE_URI_LIST)) {
            Timber.e { "Must send SelectedImageUriList to ProjectCreationActivity" }
            finish()
        } else if ((intent.extras.getParcelableArrayList<Uri>(IntentKeys.SELECTED_IMAGE_URI_LIST)).isEmpty()) {
            Timber.e { "Image Uri list must contain at least one Uri" }
            finish()
        } else {
            return intent.extras.getParcelableArrayList<Uri>(IntentKeys.SELECTED_IMAGE_URI_LIST)
        }

        return arrayListOf()
    }
}
