package com.smf.practica_fede_sergio.Retrofit

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
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
        topBar = { }
    ) { paddingValues ->
        when (uiState) {
            is MarsPhotosUiState.Success -> {
                MarsPhotosGrid(
                    photos = (uiState as MarsPhotosUiState.Success).photos,
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(top = dimensionResource(id = R.dimen.padding_16dp))
                        .fillMaxSize()
                )
            }
            is MarsPhotosUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Error: ${(uiState as MarsPhotosUiState.Error).message ?: "Unknown error"}",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            MarsPhotosUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
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
            if (photo.imgSrc != null) {
                AsyncImage(
                    model = photo.imgSrc,
                    contentDescription = "Mars Photo",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = "No image available",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}


