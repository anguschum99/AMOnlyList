package com.example.mal.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mal.MalApplication
import com.example.mal.data.MalRepository
import com.example.mal.model.Anime
import kotlinx.coroutines.launch

sealed interface AnimeUiState {
    data class Success(val animeList: List<Anime>,) : AnimeUiState
    object Error : AnimeUiState
    object Loading : AnimeUiState
}

sealed interface HomeAnimeUiState {
    data class Success(val topAnimeList: List<Anime>, val topAiringList: List<Anime>) : HomeAnimeUiState
    object Error : HomeAnimeUiState
    object Loading : HomeAnimeUiState
}



class MalViewModel(private val malRepository: MalRepository) : ViewModel() {
    var animeUiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    var topAnimeUiState: HomeAnimeUiState by mutableStateOf(HomeAnimeUiState.Loading)
        private set

    fun frog(uiState: AnimeUiState, query: String?, page: Int = 1){
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

    fun getTopAnimeList(page: Int = 1, filter: String? = null){
        viewModelScope.launch {
            topAnimeUiState = HomeAnimeUiState.Loading
            topAnimeUiState = try {
                val animeList = malRepository.getTopAnimeList(page)
                    ?: throw Exception("Anime list is null")
                val topAiringList = malRepository.getTopAnimeList(page, "airing")
                    ?: throw Exception("Anime list is null")
                HomeAnimeUiState.Success(animeList,topAiringList)
                } catch (e: Exception) {
                HomeAnimeUiState.Error
            } catch (e: retrofit2.HttpException) {
                HomeAnimeUiState.Error
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