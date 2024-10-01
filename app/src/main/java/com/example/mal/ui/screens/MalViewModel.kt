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
import com.example.mal.model.AniChara
import com.example.mal.model.Anime
import com.example.mal.model.Manga
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AnimeUiState {
    data class Success(val animeList: List<Anime>) : AnimeUiState
    object Error : AnimeUiState
    object Loading : AnimeUiState
}

sealed interface HomeAnimeUiState {
    data class Success(val topAnimeList: List<Anime>, val topAiringList: List<Anime>) :
        HomeAnimeUiState

    object Error : HomeAnimeUiState
    object Loading : HomeAnimeUiState
}

sealed interface MangaUiState {
    data class Success(val mangaList: List<Manga>) : MangaUiState
    object Error : MangaUiState
    object Loading : MangaUiState
}


data class MalUiState(
    val homeState: HomeAnimeUiState,
    val currentAnime: Anime? = null,
    val currentAnimeCharacters: List<AniChara> = emptyList()
    //val currentManga: Manga? = null
)


class MalViewModel(private val malRepository: MalRepository) : ViewModel() {
    var animeUiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    var topAnimeUiState: HomeAnimeUiState by mutableStateOf(HomeAnimeUiState.Loading)
        private set

    private val _uiState = MutableStateFlow(
        MalUiState(homeState = topAnimeUiState)
    )
    val uiState: StateFlow<MalUiState> = _uiState.asStateFlow()


    fun frog(uiState: AnimeUiState, query: String?, page: Int = 1) {
        getAnimeList(query, page)
    }

    fun getAnime() {
        _uiState.update {
            it.copy(homeState = topAnimeUiState)
        }
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

    fun getTopAnimeList(page: Int = 1, filter: String? = null) {
        viewModelScope.launch {
            topAnimeUiState = HomeAnimeUiState.Loading
            topAnimeUiState = try {
                val animeList = malRepository.getTopAnimeList(page)
                    ?: throw Exception("Anime list is null")
                val topAiringList = malRepository.getTopAnimeList(page, "airing")
                    ?: throw Exception("Anime list is null")
                HomeAnimeUiState.Success(animeList, topAiringList)
            } catch (e: Exception) {
                HomeAnimeUiState.Error
            } catch (e: retrofit2.HttpException) {
                HomeAnimeUiState.Error
            }
        }
    }

    fun getCharacterList(id: Int) {
        var ani = emptyList<AniChara>()
        viewModelScope.launch {
            try {
                val characterList = malRepository.getAnimeCharacters(id)
                    ?: throw Exception("Anime list is null")
                _uiState.update {
                    it.copy(
                        currentAnimeCharacters = characterList
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        println(ani)
    }




    fun getManga(query: String? = null, page: Int = 1, genre: String? = null) {
        viewModelScope.launch {

        }
    }


    fun updateCurrentAnime(anime: Anime) {
        getCharacterList(anime.mal_id)
        _uiState.update {
            it.copy(
                currentAnime = anime,
            )
        }
    }


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