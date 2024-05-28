package com.example.randomdogproject.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.randomdogproject.ui.theme.RandomDogProjectTheme

@Composable
fun DogProfileScreen(
    viewModel: DogProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState.value) {
        is DogProfileUiState.Profile -> {
            ProfileContent(
                url = state.photoUrl,
                onRefreshButtonClick = viewModel::refreshProfile,
                modifier = Modifier.fillMaxSize()
            )
        }

        else -> ErrorContent(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun ProfileContent(
    url: String,
    onRefreshButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // todo
    }
}

@Composable
private fun ErrorContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        // todo
    }
}

@Preview
@Composable
private fun PreviewDogProfileScreen() {
    RandomDogProjectTheme {
        DogProfileScreen()
    }
}