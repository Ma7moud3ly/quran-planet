package com.ma7moud3ly.quran.features.reading

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ma7moud3ly.quran.AppRoutes
import com.ma7moud3ly.quran.data.repository.ChaptersRepository
import com.ma7moud3ly.quran.data.repository.HistoryRepository
import com.ma7moud3ly.quran.data.repository.SettingsRepository
import com.ma7moud3ly.quran.managers.VersesManager
import com.ma7moud3ly.quran.model.Chapter
import com.ma7moud3ly.quran.model.History
import com.ma7moud3ly.quran.model.ReadingSettings
import com.ma7moud3ly.quran.platform.platformKeepScreenOn
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class ReadingViewModel(
    savedStateHandle: SavedStateHandle,
    private val chaptersRepository: ChaptersRepository,
    private val settingsRepository: SettingsRepository,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private var selectedVerseId: Int? =
        savedStateHandle.toRoute<AppRoutes.ReadingScreen>().verseId
    private var chapterId: Int = savedStateHandle.toRoute<AppRoutes.ReadingScreen>().chapterId

    private val _chapterFlow = MutableStateFlow<Chapter?>(null)
    val chapterFlow: StateFlow<Chapter?> = _chapterFlow.asStateFlow()
    val versesManager = mutableStateOf<VersesManager?>(null)

    private suspend fun initChapter(
        chapterId: Int,
        selectedVerseId: Int? = null
    ) {
        val chapter = chaptersRepository.getChapter(chapterId)
        val verseId = selectedVerseId ?: getHistory(chapterId)
        if (chapter != null) {
            _chapterFlow.emit(chapter)
            versesManager.value = VersesManager(
                verses = chapter.verses,
                initialVerseId = verseId
            )
        }
    }

    init {
        viewModelScope.launch {
            initChapter(chapterId, selectedVerseId)
        }
    }

    val readingSettingFlow: StateFlow<ReadingSettings> = settingsRepository.readingSettingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = settingsRepository.getReadingSettings()
        )

    private suspend fun getHistory(chapterId: Int): Int? {
        val history = historyRepository.getHistory(chapterId.toString())
        return history?.verseId
    }


    fun saveHistory() {
        viewModelScope.launch(NonCancellable) {
            val chapter = _chapterFlow.value ?: return@launch
            val verseId = versesManager.value?.selectedVerseId ?: 1
            if (verseId == 1) return@launch
            val history = History(
                chapterId = chapter.id,
                chapterName = chapter.name,
                verseId = verseId,
                type = History.READING
            )
            historyRepository.saveHistory(history)
        }
    }

    fun keepScreenOn(on: Boolean) {
        platformKeepScreenOn(on)
    }

    companion object {
        private const val TAG = "ReadingViewModel"
    }
}