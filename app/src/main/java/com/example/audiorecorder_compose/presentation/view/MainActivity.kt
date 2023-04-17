package com.example.audiorecorder_compose.presentation.view

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.audiorecorder_compose.presentation.viewmodel.AudioPlayerViewModel
import com.example.audiorecorder_compose.ui.theme.AudioRecorderComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: AudioPlayerViewModel by viewModels()
    private var audioFile: File? = null

    companion object {
        const val TAG_NAME = "RecordingActivity"
        const val AUDIO_FILE_NAME = "audio.mp3"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 0)
        setContent {
            AudioRecorderComposeTheme { ShowUi() }
        }
    }

    @Composable
    private fun ShowUi() {
        val recordingState = remember {
            mutableStateOf(true)
        }
        val playerState = remember {
            mutableStateOf(true)
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            OutlinedButton(onClick = { startOrStopRecording(recordingState) }) {
                val recordingText =
                    if (recordingState.value) "Start Recording" else "Stop Recording"
                Text(text = recordingText)
            }
            OutlinedButton(onClick = { startOrStopPlayingAudio(playerState) }) {
                val audioPlayerText = if (playerState.value) "Play Audio" else "Stop Audio"
                Text(text = audioPlayerText)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Rounded.Info, contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Note: You can record upto 1 min.", fontStyle = FontStyle.Italic)
            }

        }
    }

    private fun startOrStopRecording(recordingState: MutableState<Boolean>) {
        if (recordingState.value)
            File(cacheDir, AUDIO_FILE_NAME).also {
                viewModel.startRecording(it)
                audioFile = it
                recordingState.value = false
                Log.d(TAG_NAME, "recordingState: ${recordingState.value}")
            } else {
            viewModel.stopRecording()
            recordingState.value = true
            Log.d(TAG_NAME, "recordingState: ${recordingState.value}")
        }
    }

    private fun startOrStopPlayingAudio(playerState: MutableState<Boolean>) {
        if (playerState.value) {
            viewModel.startAudioPlayer(audioFile ?: return)
            playerState.value = false
            Log.d(TAG_NAME, "playerState: ${playerState.value}")
        } else {
            viewModel.stopAudioPlayer()
            playerState.value = true
            Log.d(TAG_NAME, "playerState: ${playerState.value}")
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    @Preview
    @Composable
    fun showUiPreview() {
        ShowUi()
    }
}
