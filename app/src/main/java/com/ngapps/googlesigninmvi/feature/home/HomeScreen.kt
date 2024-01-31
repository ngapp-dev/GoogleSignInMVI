package com.ngapps.googlesigninmvi.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ngapps.googlesigninmvi.core.designsystem.DynamicAsyncImage

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeState by viewModel.homeState.collectAsStateWithLifecycle()
    HomeScreen(
        state = homeState,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier = Modifier,
) {
    when (state) {
        HomeUiState.Error -> Unit
        is HomeUiState.Success -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .testTag("home:screen"),
            ) {
                item {
                    Column {
                        DynamicAsyncImage(
                            imageUrl = state.auth.token,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(114.dp),
                        )
                        Text(
                            text = "${state.auth.givenName} ${state.auth.familyName}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 18.sp,
                            ),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = modifier,
                        )
                    }
                }
                item {
                    Column {
                        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                    }
                }
            }
        }
    }
}