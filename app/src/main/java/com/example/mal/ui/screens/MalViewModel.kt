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
import com.example.mal.model.anime.Anime
import com.example.mal.model.anime.AnimeSummary
import com.example.mal.model.anime.Data
import com.example.mal.model.manga.Manga
import com.example.mal.model.manga.MangaSummary
import com.example.mal.model.seasons.Season
import com.example.mal.model.seasons.SeasonData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AnimeUiState {
    data class Success(val animeList: List<AnimeSummary>) : AnimeUiState
    object Error : AnimeUiState
    object Loading : AnimeUiState
}

sealed interface soloAnimeUiState {
    data class Success(val anime: Anime) : soloAnimeUiState
    object Error : soloAnimeUiState
    object Loading : soloAnimeUiState
}

sealed interface HomeAnimeUiState {
    data class Success(val topAnimeList: List<AnimeSummary>, val topAiringList: List<AnimeSummary>) :
        HomeAnimeUiState

    object Error : HomeAnimeUiState
    object Loading : HomeAnimeUiState
}

sealed interface MangaUiState {
    data class Success(val mangaList: List<MangaSummary>) : MangaUiState
    object Error : MangaUiState
    object Loading : MangaUiState
}

sealed interface soloMangaUiState{
    data class Success(val manga: Manga) : soloMangaUiState
    object Error : soloMangaUiState
    object Loading : soloMangaUiState
}

sealed interface CurrentSeasonUiState {
    data class Success(val seasonList: Season, val page: Int = 1) : CurrentSeasonUiState
    object Error : CurrentSeasonUiState
    object Loading : CurrentSeasonUiState
}

sealed interface SeasonListUiState {
    data class Success(val bigSeasonList: List<SeasonData>) : SeasonListUiState
    object Error : SeasonListUiState
    object Loading : SeasonListUiState
}


data class MalUiState(
    val homeState: HomeAnimeUiState,
    val mangaState: MangaUiState,
    val animeState: AnimeUiState,
    val currentAnime: Anime? = null,
    val currentAnimeID: Int? = null,
    val currentAnimeCharacters: List<AniChara> = emptyList(),
    val currentManga: Manga? = null,
    val currentMangaID: Int? = null
)

data class searchUiState(
    val aniUiState: AnimeUiState,
    val mangaUiState: MangaUiState
)


class MalViewModel(private val malRepository: MalRepository) : ViewModel() {
    var animeUiState: AnimeUiState by mutableStateOf(AnimeUiState.Loading)
        private set

    var soloAnime: soloAnimeUiState by mutableStateOf(soloAnimeUiState.Loading)

    var topAnimeUiState: HomeAnimeUiState by mutableStateOf(HomeAnimeUiState.Loading)
        private set

    var mangaUiState: MangaUiState by mutableStateOf(MangaUiState.Loading)
        private set

    var currentSeasonState: CurrentSeasonUiState by mutableStateOf(CurrentSeasonUiState.Loading)
        private set

    var seasonListState: SeasonListUiState by mutableStateOf(SeasonListUiState.Loading)
        private set

    private val _uiState = MutableStateFlow(
        MalUiState(
            homeState = topAnimeUiState,
            mangaState = mangaUiState,
            animeState = animeUiState
        )
    )
    val uiState: StateFlow<MalUiState> = _uiState.asStateFlow()




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

    fun getManga(query: String? = null, page: Int = 1, genre: String? = null) {
        viewModelScope.launch {
            mangaUiState = MangaUiState.Loading
            mangaUiState = try {
                val mangaList = malRepository.getManga(query ?: "", page, genre)
                    ?: throw Exception("Manga list is null")
                MangaUiState.Success(mangaList)
            } catch (e: Exception) {
                MangaUiState.Error
            } catch (e: retrofit2.HttpException) {
                MangaUiState.Error
            }
        }
    }

    fun altGetAnimeFull(id:Int){
        viewModelScope.launch {
            soloAnime = soloAnimeUiState.Loading
            soloAnime = try {
                val anime = malRepository.getAnimeFull(id)
                    ?: throw Exception("Anime is null")
                _uiState.update{
                    it.copy(
                        currentAnime = anime,
                        currentAnimeID = id
                    )
                }
                soloAnimeUiState.Success(anime = anime)


                } catch (e: Exception) {
                soloAnimeUiState.Error
            } catch (e: retrofit2.HttpException) {
                soloAnimeUiState.Error
            }
        }
    }


    fun getAnimeFull(id: Int){
        viewModelScope.launch {
            val anime = malRepository.getAnimeFull(id)
                ?: throw Exception("Anime is null")
            _uiState.update {
                it.copy(
                    currentAnime = anime
                )
            }
        }
    }

    fun getMangaFull(id: Int){
        viewModelScope.launch {
            val manga = malRepository.getMangaFull(id)
                ?: throw Exception("Manga list is null")
            _uiState.update {
                it.copy(
                    currentManga = manga,
                    currentMangaID = id
                )
            }
        }
    }

    fun getTopAnimeList(page: Int = 1, filter: String? = null) {
        viewModelScope.launch {
            topAnimeUiState = HomeAnimeUiState.Loading

            delay(1000)

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
    }

    fun getCurrentSeason(page: Int = 1) {
        viewModelScope.launch {
            currentSeasonState = CurrentSeasonUiState.Loading
            currentSeasonState = try {
                val seasonList = malRepository.getSeasonNow(page)
                    ?: throw Exception("Season list is null")
                CurrentSeasonUiState.Success(seasonList)
            } catch (e: Exception) {
                CurrentSeasonUiState.Error
            } catch (e: retrofit2.HttpException) {
                CurrentSeasonUiState.Error
            }
        }
    }

    fun getSeasonList() {
        viewModelScope.launch {
            seasonListState = SeasonListUiState.Loading
            seasonListState = try {
                val seasonList = malRepository.getSeasonList()
                    ?: throw Exception("Season list is null")
                SeasonListUiState.Success(seasonList.data)
            } catch (e: Exception) {
                SeasonListUiState.Error
            }
        }
    }





    fun updateCurrentManga(mal_id: Int) {
        getMangaFull(mal_id)
    }


    fun updateCurrentAnime(malId: Int) {
        altGetAnimeFull(malId)
        getCharacterList(malId)
    }


    var selectedTabIndex: Int by mutableIntStateOf(0)

    var userInput: String by mutableStateOf("")
        private set

    fun onUserInputChanged(input: String) {
        userInput = input
    }

    init {

        viewModelScope.launch {
            delay(timeMillis = 1000)
            getTopAnimeList()
            delay(timeMillis = 5000)
            getAnimeList()
            delay(timeMillis = 5000)
            getManga()
            delay(timeMillis = 1000)
            getCurrentSeason()
            delay(timeMillis = 1000)
            getSeasonList()
        }

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