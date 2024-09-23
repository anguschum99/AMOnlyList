package com.example.mal.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MalSearch(
    query: String,
    onQueryChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    active: (Boolean) -> Unit,
) {
    SearchBar(
        query = query,
        onQueryChange = { onQueryChanged(it) },
        onSearch = { onKeyboardDone() },
        active = false,
        onActiveChange = active,
        placeholder = { Text("Search") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        windowInsets = androidx.compose.foundation.layout.WindowInsets(5.dp),
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp),
        colors = androidx.compose.material3.SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,
            dividerColor = MaterialTheme.colorScheme.onSurface
        )
    ) {

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MalSearch(
                query = text,
                onQueryChanged = { text = it },
                onKeyboardDone = { active = false },
                active = { active = it },
            )
        }
    ) { innerPadding ->
    }
}