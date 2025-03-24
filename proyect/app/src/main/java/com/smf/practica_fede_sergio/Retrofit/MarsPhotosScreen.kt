package com.smf.practica_fede_sergio.Retrofit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smf.practica_fede_sergio.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarsPhotosScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: MarsPhotoViewModel = MarsPhotoViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = { /* Add your top bar content here */ }
    ) { paddingValues ->
        MarsPhotosGrid(
            photos = uiState.marsPhotos,
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = dimensionResource(id = R.dimen.padding_16dp))
                .fillMaxSize()
        )
    }
}

@Composable
fun MarsPhotosGrid(
    photos: List<MarsPhotos>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_16dp))
    ) {
        items(photos) { photo ->
            MarsPhotoCard(photo = photo)
        }
    }
}

@Composable
fun MarsPhotoCard(
    photo: MarsPhotos,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_8dp))
    ) {
        Box(modifier = Modifier.height(200.dp)) {

        }
    }
}