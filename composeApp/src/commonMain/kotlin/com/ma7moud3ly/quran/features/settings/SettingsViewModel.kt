package com.ma7moud3ly.quran.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud3ly.quran.data.repository.SettingsRepository
import com.ma7moud3ly.quran.model.AppFont
import com.ma7moud3ly.quran.model.AppSettings
import com.ma7moud3ly.quran.model.ReadingSettings
import com.ma7moud3ly.quran.model.RecitationSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val fontList: List<AppFont> = settingsRepository.fontList

    val darkModeFlow: StateFlow<Boolean> = settingsRepository.darkModeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = settingsRepository.darkMode
        )


    fun toggleDarkMode() {
        viewModelScope.launch {
            settingsRepository.toggleDarkMode()
        }
    }

    private val _settingsFlow = MutableStateFlow<AppSettings?>(null)
    val settingsFlow: StateFlow<AppSettings?> = _settingsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun getSettings(ofReading: Boolean) {
        viewModelScope.launch {
            val settings = if (ofReading) settingsRepository.getReadingSettings()
            else settingsRepository.getRecitationSettings()
            _settingsFlow.value = settings
        }
    }

    fun updateSettings(settings: AppSettings) {
        viewModelScope.launch {
            if (settings is ReadingSettings)
                settingsRepository.updateReadingSettings(settings)
            else if (settings is RecitationSettings)
                settingsRepository.updateRecitationSettings(settings)
        }
    }
}