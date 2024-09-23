package com.example.mal.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mal.MalApplication
import com.example.mal.data.MalRepository
import com.example.mal.data.ScreenType
import com.example.mal.model.Anime
import com.example.mal.model.AnimeList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface AnimeUiState {
    data class Success(val animeList: List<Anime>,) : AnimeUiState
    object Error : AnimeUiState
    object Loading : AnimeUiState
}




class MalViewModel(private val malRepository: MalRepository) : ViewModel() {
    var animeUiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    var topAnimeUiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    fun frog(uiState: AnimeUiState,query: String?, page: Int = 1){
        getAnimeList(query, page)
    }

    fun getAnimeList(query: String? = null, page: Int = 1, genre: String? = null) {
        viewModelScope.launch {
            animeUiState = AnimeUiState.Loading
            animeUiState = try {
                val animeList = malRepository.getAnimeList(query ?: "", page, genre)
                    ?: throw Exception("Anime list is null")
                AnimeUiState.Success(animeList)
            } catch (e: Exception) {
                AnimeUiState.Error
            } catch (e: retrofit2.HttpException) {
                AnimeUiState.Error
            }
        }
    }

    fun getTopAnimeList(page: Int = 1){
        viewModelScope.launch {
            topAnimeUiState = AnimeUiState.Loading
            topAnimeUiState = try {
                val animeList = malRepository.getTopAnimeList(page)
                    ?: throw Exception("Anime list is null")
                AnimeUiState.Success(animeList)
                } catch (e: Exception) {
                AnimeUiState.Error
            } catch (e: retrofit2.HttpException) {
                AnimeUiState.Error
            }
        }
    }

    var currentAnime: Anime? = null

    var selectedTabIndex: Int by mutableIntStateOf(0)

    var userInput: String by mutableStateOf("")
        private set

    fun onUserInputChanged(input: String) {
        userInput = input
    }

    init {
        getAnimeList()
        getTopAnimeList()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MalApplication)
                val malRepository = application.container.malRepository
                MalViewModel(malRepository = malRepository)
            }
        }
    }


}