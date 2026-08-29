package com.ma7moud3ly.quran.features.recitation.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud3ly.quran.data.repository.HistoryRepository
import com.ma7moud3ly.quran.data.repository.RecitationRepository
import com.ma7moud3ly.quran.data.repository.SettingsRepository
import com.ma7moud3ly.quran.managers.BackgroundsManager
import com.ma7moud3ly.quran.managers.MediaPlayerManager
import com.ma7moud3ly.quran.model.History
import com.ma7moud3ly.quran.model.RecitationSettings
import com.ma7moud3ly.quran.model.ScreenMode
import com.ma7moud3ly.quran.model.toInt
import com.ma7moud3ly.quran.platform.Log
import com.ma7moud3ly.quran.platform.platformKeepScreenOn
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@OptIn(FlowPreview::class)
@KoinViewModel
class PlaybackViewModel(
    private val settingsRepository: SettingsRepository,
    private val historyRepository: HistoryRepository,
    private val recitationRepository: RecitationRepository,
    private val mediaPlayerManager: MediaPlayerManager,
    private val backgroundsManager: BackgroundsManager,
) : ViewModel() {

    fun getMediaPlayerManager() = mediaPlayerManager
    fun getBgManager() = backgroundsManager

    init {
        mediaPlayerManager.initPlayBack()
    }

    override fun onCleared() {
        super.onCleared()
        Log.v(TAG, "onCleared")
        saveHistory()
        if (mediaPlayerManager.playInBackground.not()) {
            mediaPlayerManager.release()
        }
        if (mediaPlayerManager.singleReciter()) {
            val lastPlayedVerseId = mediaPlayerManager.selectedVerseId
            recitationRepository.recitationState.value.setFirstVerse(lastPlayedVerseId)
        }
    }

    val settingFlow: StateFlow<RecitationSettings> = settingsRepository.recitationSettingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = settingsRepository.getRecitationSettings()
        )


    fun keepScreenOn(on: Boolean) {
        platformKeepScreenOn(on)
    }

    private fun saveHistory() {
        Log.v(TAG, "saveHistory")
        viewModelScope.launch(NonCancellable) {
            val recitation = recitationRepository.getRecitation()
            if (recitation.singleReciter()) {
                val history = History(
                    type = History.LISTENING,
                    chapterId = recitation.chapter.id,
                    chapterName = recitation.chapter.name,
                    reciterId = recitation.currentReciter.id,
                    reciterName = recitation.currentReciter.name,
                    verseId = mediaPlayerManager.selectedVerseId,
                    screenMode = if (recitation.screenMode is ScreenMode.Normal) 1 else 2,
                    reelMode = recitation.reelMode,
                    playInBackground = recitation.playInBackground,
                    playLocally = recitation.playLocally,
                    playbackMode = recitation.playbackMode.toInt
                )
                historyRepository.saveHistory(history)
            }
        }
    }

    companion object {
        private const val TAG = "PlaybackViewModel"
    }
}