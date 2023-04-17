package com.example.audiorecorder_compose.data.repo

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.provider.MediaStore.Audio.Media
import dagger.hilt.android.qualifiers.ActivityContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

/**
 * Created by Harjot Singh on 01/03/23
 */
internal interface AudioRecorderRepo {
    fun start(outputFile: File)
    fun stop()
}

internal class AudioRecorderRepoImpl @Inject constructor(
    private val context: Context,
    private var recorder: MediaRecorder? = null
) : AudioRecorderRepo {
    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) MediaRecorder(context) else MediaRecorder()
    }

    override fun start(outputFile: File) {
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare()
            start()

            recorder = this
        }
    }

    override fun stop() {
        recorder?.stop()
        recorder?.reset()
        recorder = null
    }

}
