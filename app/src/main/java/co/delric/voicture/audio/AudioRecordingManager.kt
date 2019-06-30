package co.delric.voicture.audio

import android.media.MediaRecorder
import com.github.ajalt.timberkt.Timber
import java.io.File
import java.io.IOException

class AudioRecordingManager : MediaRecorder.OnErrorListener, MediaRecorder.OnInfoListener {
    private lateinit var recorder: MediaRecorder
    private var state: RecordingState = RecordingState.STOPPED

    enum class RecordingState {
        RECORDING,
        STOPPED
    }

    fun startRecording(file: File) {
        if (state != RecordingState.RECORDING) {
            recorder = MediaRecorder()

            recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder.setOutputFile(file.absolutePath)

            recorder.setOnErrorListener(this)
            recorder.setOnInfoListener(this)

            try {
                recorder.prepare()
                recorder.start()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            state = RecordingState.RECORDING
        }
    }

    fun stopRecording() {
        if (state == RecordingState.RECORDING) {
            recorder.stop()
            recorder.reset()
            recorder.release()
            state = RecordingState.STOPPED
        }
    }

    fun getState() = state

    override fun onInfo(mr: MediaRecorder?, what: Int, extra: Int) {
        Timber.d { "$this.javaClass.simpleName: Info $what" }
    }

    override fun onError(mr: MediaRecorder?, what: Int, extra: Int) {
        Timber.d { "$this.javaClass.simpleName: Error $what" }
    }
}