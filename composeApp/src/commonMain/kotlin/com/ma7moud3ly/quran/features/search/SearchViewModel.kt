package com.ma7moud3ly.quran.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ma7moud3ly.quran.data.repository.ChaptersRepository
import com.ma7moud3ly.quran.data.repository.RecitersRepository
import com.ma7moud3ly.quran.model.Chapter
import com.ma7moud3ly.quran.model.Reciter
import com.ma7moud3ly.quran.model.SearchQuery
import com.ma7moud3ly.quran.model.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class SearchViewModel(
    private val chaptersRepository: ChaptersRepository,
    private val recitersRepository: RecitersRepository
) : ViewModel() {

    private val _recitersResult = MutableStateFlow<List<Reciter>?>(null)
    val recitersResult: Flow<List<Reciter>?> = _recitersResult.asStateFlow()

    private val _chaptersResult = MutableStateFlow<List<Chapter>?>(null)
    val chaptersResult: Flow<List<Chapter>?> = _chaptersResult.asStateFlow()

    private val _versesResult = MutableStateFlow<List<SearchResult>?>(null)
    val versesResult: Flow<List<SearchResult>?> = _versesResult.asStateFlow()

    fun search(query: SearchQuery) {
        viewModelScope.launch {
            if (query.verses) {
                chaptersRepository.searchVerses(query.query).collect {
                    _versesResult.value = it
                }
            } else {
                _versesResult.value = null
            }
            if (query.chapters) {
                chaptersRepository.searchChapters(query.query).collect {
                    _chaptersResult.value = it
                }
            } else {
                _chaptersResult.value = null
            }
            if (query.reciters) {
                recitersRepository.searchReciters(query.query).collect {
                    _recitersResult.value = it
                }
            } else {
                _recitersResult.value = null
            }
        }
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}