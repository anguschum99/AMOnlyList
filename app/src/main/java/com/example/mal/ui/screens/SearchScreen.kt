@file:OptIn(ExperimentalFoundationApi::class)

package com.example.mal.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mal.model.Anime
import com.example.mal.model.manga.MangaList
import com.example.mal.model.manga.MangaSummary
import com.example.mal.ui.components.MalSearch
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SearchScreen(
    viewModel: MalViewModel,
    aniUiState: AnimeUiState,
    mangaUiState: MangaUiState,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    onClick: (Anime) -> Unit,
    mangaOnClick: (MangaSummary) -> Unit
) {
    Column(modifier = modifier) {
        TabScreen(
            viewModel = viewModel,
            aniUiState = aniUiState,
            mangaUiState = mangaUiState,
            modifier = modifier,
            contentPaddingValues = contentPaddingValues,
            onClick = onClick,
            mangaOnClick = mangaOnClick

        )
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabScreen(
    viewModel: MalViewModel,
    aniUiState: AnimeUiState,
    mangaUiState: MangaUiState,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    onClick: (Anime) -> Unit,
    mangaOnClick: (MangaSummary) -> Unit
) {
    // Create a pager state with the number of tabs
    val pagerState = rememberPagerState {
        tabItems.size
    }
    // Remembers state of search bar
    var active by remember { mutableStateOf(false) }


    LaunchedEffect(viewModel.selectedTabIndex) {
        // Animate to the selected tab index when it changes
        pagerState.animateScrollToPage(viewModel.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        // Update the selected tab index when the current page changes
        if (!pagerState.isScrollInProgress) {
            viewModel.selectedTabIndex = pagerState.currentPage
        }

    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(),

        ) {
        // Tab Row
        TabRow(
            selectedTabIndex = viewModel.selectedTabIndex,
            modifier = Modifier.padding()
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = viewModel.selectedTabIndex == index,
                    onClick = { viewModel.selectedTabIndex = index },
                    text = { Text(text = item.title) }
                )
            }
        }

        // Horizontal Pager
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { index ->
            if (pagerState.currentPage == 0) {
                Scaffold(
                    topBar = {
                        MalSearch(
                            query = viewModel.userInput,
                            onQueryChanged = { viewModel.onUserInputChanged(it) },
                            onKeyboardDone = { viewModel.getAnimeList(viewModel.userInput) },
                            active = { active = it }
                        )

                    },
                    contentWindowInsets = WindowInsets(0.dp),
                ) { innerPadding ->

                    when (aniUiState) {
                        is AnimeUiState.Loading -> Text(text = "Loading")
                        is AnimeUiState.Success -> {
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                PhotoGrid(
                                    list = aniUiState.animeList,
                                    onClick = onClick
                                )
                            }
                        }

                        is AnimeUiState.Error -> Text(text = "Error")
                    }


                }

            } else if (pagerState.currentPage == 1) {
                Scaffold(
                    topBar = {
                        MalSearch(
                            query = viewModel.userInput,
                            onQueryChanged = { viewModel.onUserInputChanged(it) },
                            onKeyboardDone = { viewModel.getManga(viewModel.userInput) },
                            active = { active = it }
                        )

                    },
                    contentWindowInsets = WindowInsets(0.dp),
                ) { innerPadding ->

                    when (mangaUiState) {
                        is MangaUiState.Loading -> Text(text = "Loading")
                        is MangaUiState.Success -> {
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                MangaGrid(list = mangaUiState.mangaList, oncClick = mangaOnClick)
                            }
                        }

                        is MangaUiState.Error -> Text(text = "Error")
                    }


                }


            }
        }
    }

}

@Composable
fun MangaGrid(list: List<MangaSummary>, oncClick: (MangaSummary) -> Unit) {
    LazyColumn() {
        items(items = list, key = { manga -> manga.mal_id }) { manga ->
            MangaColumn(manga = manga, onClick = { oncClick(manga) })
        }
    }
}

@Composable
fun MangaColumn(
    manga: MangaSummary,
    onClick: (MangaSummary) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = CardDefaults.outlinedCardBorder(),
        onClick = { onClick(manga) }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(manga.images.jpg.image_url)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(end = 8.dp)
                )

                Column {
                    Text(manga.title, style = MaterialTheme.typography.titleMedium)
                    Text(manga.status)
                    Text(manga.type)
                    Text("${manga.score.toString()} Rating")
                }

//                Spacer(Modifier.weight(1f))
//
//                Text(
//                    manga.score.toString(),
//                    modifier = Modifier
//                        .padding(end = 28.dp)
//                        .align(Alignment.CenterVertically),
//                    style = MaterialTheme.typography.titleLarge
//                )
            }

        }


    }


}



@Composable
fun AnimeCard(
    anime: Anime,
    onClick: (Anime) -> Unit,
    modifier: Modifier = Modifier
) {


    Column(modifier = modifier) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            onClick = { onClick(anime) },
            modifier = Modifier.padding(8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(anime.images.jpg.image_url)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .aspectRatio(1f)
                        .padding(end = 1.dp)
                )
                Text(anime.title, modifier = Modifier.padding(8.dp))
                Text("")

            }
        }
    }
}

// Individual Card Handler
@Composable
fun AnimeColumn(anime: Anime, onClick: (Anime) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = CardDefaults.outlinedCardBorder(),
        onClick = { onClick(anime) }
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(anime.images.jpg.image_url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(end = 8.dp)
            )

            Column(
                modifier = Modifier.padding(5.dp),
            ) {
                Text(
                    anime.title,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        anime.type,
                        modifier = Modifier
                            .background(
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(5.dp),
                                color = if (anime.type == "TV") {
                                    androidx.compose.ui.graphics.Color.Green
                                } else {
                                    androidx.compose.ui.graphics.Color.Yellow
                                }
                            )
                            .padding(3.dp)
                    )
                    Text(" ${anime.episodes} episodes")
                }

                Text("${anime.score} Rating")
                Text(
                    text =
                    if (anime.year == 0) {
                        ""
                    } else run {
                        anime.year.toString()
                    }
                )

            }

        }
    }
}


@Composable
fun PhotoGrid(
    list: List<Anime>,
    onClick: (Anime) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 5.dp),
    ) {
        LazyColumn() {
            items(items = list, key = { anime -> anime.mal_id }) { anime ->
                AnimeColumn(anime = anime, onClick = { onClick(anime) })
            }
        }
    }
}


data class TabItem(
    val title: String
)

val tabItems = listOf(
    TabItem(title = "Anime"),
    TabItem(title = "Manga"),
)

