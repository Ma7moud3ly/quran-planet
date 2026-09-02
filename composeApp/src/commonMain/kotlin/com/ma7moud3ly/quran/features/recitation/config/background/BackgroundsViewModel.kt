package com.ma7moud3ly.quran.features.recitation.config.background

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud3ly.quran.data.repository.BackgroundsRepository
import com.ma7moud3ly.quran.model.TvBackground
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class BackgroundsViewModel(
    private val backgroundsRepository: BackgroundsRepository
) : ViewModel() {

    val backgroundsFlow = backgroundsRepository.backgroundsFlow
    val selectedBackground = backgroundsRepository.selectedBackground

    fun selectBackground(tvBackground: TvBackground) {
        backgroundsRepository.selectBackground(tvBackground)
    }

    fun removeBackground(tvBackground: TvBackground) {
        viewModelScope.launch {
            backgroundsRepository.removeBackground(tvBackground)
        }
    }

    fun addNewBackground() {
        viewModelScope.launch {
            backgroundsRepository.addNewBackground()
        }
    }
}