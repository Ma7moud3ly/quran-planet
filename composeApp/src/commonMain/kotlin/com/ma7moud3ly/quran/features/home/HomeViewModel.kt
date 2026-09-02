package com.ma7moud3ly.quran.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud3ly.quran.data.repository.BookmarksRepository
import com.ma7moud3ly.quran.data.repository.ChaptersRepository
import com.ma7moud3ly.quran.data.repository.RecitersRepository
import com.ma7moud3ly.quran.model.Bookmark
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val chaptersRepository: ChaptersRepository,
    private val recitersRepository: RecitersRepository,
    private val bookmarksRepository: BookmarksRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            bookmarksRepository.initBookmarks()
        }
    }

    val bookmarksFlow = bookmarksRepository.bookmarksFlow

    fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            bookmarksRepository.deleteBookmark(bookmark)
        }
    }

    val chaptersIndexFlow = flow { emit(chaptersRepository.getIndex()) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val recitersIndexFlow = flow { emit(recitersRepository.getRecitersIndex()) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

}