package com.ma7moud3ly.quran.features.recitation.download

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud3ly.quran.data.repository.DownloadsRepository
import com.ma7moud3ly.quran.model.Recitation
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class DownloadsViewModel(
    private val downloadsRepository: DownloadsRepository
) : ViewModel() {

    val downloadProgress = downloadsRepository.downloadProgress.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        replay = 0
    )

    val downloadComplete = downloadsRepository.downloadComplete.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        replay = 0
    )


    fun downloadChapter(recitation: Recitation) {
        viewModelScope.launch {
            withContext(NonCancellable) {
                downloadsRepository.downloadChapter(
                    downloadId = recitation.downloadDirectory,
                    url = recitation.downloadLink,
                    outputPath = recitation.downloadDirectory
                )
            }
        }
    }
}