package com.delricco.vince.voicture.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
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
import com.delricco.vince.voicture.commons.serialization.VoictureProjectSerDes
import com.delricco.vince.voicture.commons.sharedprefs.SavedProject
import com.delricco.vince.voicture.intents.IntentKeys
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
    @Inject protected lateinit var savedProjectPrefs: SavedProject
    @Inject protected lateinit var voictureProjectSerDes: VoictureProjectSerDes

    private lateinit var voictureProject: VoictureProject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_creation)
        VoictureApplication.activityComponent.inject(this)
        if (!initVoictureProject(savedInstanceState)) {
            finishActivityOnError("Failed to initialize voicture project")
            return
        }
        imageViewer.adapter = ImageViewerAdapter(supportFragmentManager, voictureProject.getImageUriList())
        imageViewer.addOnPageChangeListener(this)
        indicator.setViewPager(imageViewer)
        recordingOnOff.setOnClickListener { onRecordButtonClicked() }
        playAudio.setOnClickListener { onPlayAudioButtonClicked() }
        previewVoictureProject.setOnClickListener { onPreviewVoictureProjectClicked() }
    }

    private fun finishActivityOnError(error: String) {
        Timber.e { error }
        finish()
    }

    private fun initVoictureProject(savedInstanceState: Bundle?): Boolean {
        voictureProject = if (savedInstanceState != null && savedInstanceState.containsKey(IntentKeys.VOICTURE_PROJECT)) {
            voictureProjectSerDes.fromJson(savedInstanceState.getString(IntentKeys.VOICTURE_PROJECT))
        } else if (intent.extras != null && intent.extras.containsKey(IntentKeys.VOICTURE_PROJECT)) {
            voictureProjectSerDes.fromJson(intent.getStringExtra(IntentKeys.VOICTURE_PROJECT))
        } else {
            return false
        }
        if (voictureProject.data.isEmpty()) {
            return false
        }
        return true
    }

    override fun onPause() {
        audioPlaybackManager.stop()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(IntentKeys.VOICTURE_PROJECT, voictureProjectSerDes.toJson(voictureProject))
        super.onSaveInstanceState(outState)
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
        savedProjectPrefs.saveProject(VoictureProject(voictureProject.data, "Test"))
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

    private fun onPlayAudioButtonClicked() {
        if (audioPlaybackManager.isPlaying()) {
            audioPlaybackManager.stop()
            playAudio.setImageResource(android.R.drawable.ic_media_play)
        } else {
            audioPlaybackManager.playAudio(currentVoicture().audioFile!!, MediaPlayer.OnCompletionListener {
                playAudio.setImageResource(android.R.drawable.ic_media_play)
            })
            playAudio.setImageResource(android.R.drawable.ic_media_pause)
        }
    }

    private fun onPreviewVoictureProjectClicked() {
        val intent = Intent(applicationContext, PreviewVoictureProjectActivity::class.java)
        intent.putExtra(IntentKeys.VOICTURE_PROJECT, voictureProjectSerDes.toJson(voictureProject))
        startActivity(intent)
    }

    private fun currentVoicture() = voictureProject.data[imageViewer.currentItem]
}
