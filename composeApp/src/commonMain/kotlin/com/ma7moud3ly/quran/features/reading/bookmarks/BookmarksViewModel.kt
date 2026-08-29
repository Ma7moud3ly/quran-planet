package com.ma7moud3ly.quran.features.reading.bookmarks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ma7moud3ly.quran.AppRoutes
import com.ma7moud3ly.quran.data.repository.BookmarksRepository
import com.ma7moud3ly.quran.data.repository.ChaptersRepository
import com.ma7moud3ly.quran.model.Bookmark
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class BookmarksViewModel(
    savedStateHandle: SavedStateHandle,
    private val chaptersRepository: ChaptersRepository,
    private val bookmarksRepository: BookmarksRepository
) : ViewModel() {

    private val chapterId = savedStateHandle.toRoute<AppRoutes.BookmarksDialog>().chapterId
    val selectedVerseId = savedStateHandle.toRoute<AppRoutes.BookmarksDialog>().verseId

    val chapterFlow = flow {
        emit(chaptersRepository.getChapter(chapterId))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    suspend fun addBookmark(verseId: Int) {
        val bookmark = Bookmark(
            chapterId = chapterId,
            verseId = verseId
        )
        bookmarksRepository.saveBookmark(bookmark)
    }
}