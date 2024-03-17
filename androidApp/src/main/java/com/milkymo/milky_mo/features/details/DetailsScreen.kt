package com.milkymo.milky_mo.features.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.milkymo.milky_mo.R
import com.milkymo.milky_mo.component.Toast

@Composable
fun DetailsScreen(detailsViewModel: DetailsViewModel) {
    val bookListUpdateState by detailsViewModel.updateState.collectAsState()

    when (val state = bookListUpdateState) {
        DetailsViewModel.UiState.ErrorFromAPI -> Toast("Error from API")
        DetailsViewModel.UiState.LoadingFromAPI -> Unit
        is DetailsViewModel.UiState.Success -> {
            Column(
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.book_title, state.book.title),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}
