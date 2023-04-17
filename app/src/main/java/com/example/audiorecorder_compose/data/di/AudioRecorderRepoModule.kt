package com.example.audiorecorder_compose.data.di

import android.content.Context
import com.example.audiorecorder_compose.data.repo.AudioRecorderRepo
import com.example.audiorecorder_compose.data.repo.AudioRecorderRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by Harjot Singh on 01/03/23
 */
@Module
@InstallIn(ViewModelComponent::class)
internal class AudioRecorderRepoModule {
    @Provides
    @ViewModelScoped
    fun provideAudioRecorderRepo(@ApplicationContext context: Context): AudioRecorderRepo {
        return AudioRecorderRepoImpl(context, null)
    }
}