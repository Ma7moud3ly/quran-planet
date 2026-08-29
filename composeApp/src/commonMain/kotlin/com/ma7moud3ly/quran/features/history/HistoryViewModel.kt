package com.ma7moud3ly.quran.features.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud3ly.quran.data.repository.HistoryRepository
import com.ma7moud3ly.quran.model.History
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            historyRepository.initHistory()
        }
    }

    val historyFlow = historyRepository.historyFlow

    fun deleteHistory(history: History) {
        viewModelScope.launch {
            historyRepository.deleteHistory(history)
        }
    }
}