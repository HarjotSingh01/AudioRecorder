package com.example.audiorecorder_compose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.audiorecorder_compose.data.repo.AudioPlayerRepo
import com.example.audiorecorder_compose.data.repo.AudioRecorderRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

/**
 * Created by Harjot Singh on 01/03/23
 */
@HiltViewModel
internal class AudioPlayerViewModel @Inject constructor(
    private val audioRecorderRepo: AudioRecorderRepo,
    private val audioPlayerRepo: AudioPlayerRepo
) : ViewModel() {
    fun startRecording(file: File) {
        audioRecorderRepo.start(file)
    }

    fun stopRecording() {
        audioRecorderRepo.stop()
    }

    fun startAudioPlayer(file: File) {
        audioPlayerRepo.playFile(file)
    }

    fun stopAudioPlayer() {
        audioPlayerRepo.stop()
    }
}