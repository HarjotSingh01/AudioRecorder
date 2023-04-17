package com.example.audiorecorder_compose.data.repo

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File
import javax.inject.Inject

/**
 * Created by Harjot Singh on 01/03/23
 */
internal interface AudioPlayerRepo {
    fun playFile(file: File)
    fun stop()
}

internal class AudioPlayerRepoImpl @Inject constructor(
    private val context: Context,
    private var player: MediaPlayer? = null
) : AudioPlayerRepo {
    override fun playFile(file: File) {
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            start()
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }

}