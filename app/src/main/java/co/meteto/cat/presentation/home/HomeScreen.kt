package co.meteto.cat.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import co.meteto.cat.core.app.AppPreferences
import co.meteto.cat.domain.model.Cat
import co.meteto.cat.presentation.home.component.ItemCat
import co.meteto.cat.presentation.util.ErrorMessage
import co.meteto.cat.presentation.util.LoadingNextPageItem
import co.meteto.cat.presentation.util.PageLoader
import co.meteto.cat.presentation.util.resource.route.AppScreen

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    LaunchedEffect(viewModel) {
        viewModel.effect.collect {
            when (it) {
                is HomeEffect.NavigateDetails -> {
                    navController.navigate(AppScreen.DetailsScreen.route)
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsState()
    val pagingItems: LazyPagingItems<Cat> = viewModel.catsState.collectAsLazyPagingItems()
    Box(Modifier.fillMaxSize()) {
        LazyVerticalGrid(GridCells.Fixed(3)) {
            items(pagingItems.itemCount) { index ->
                ItemCat(
                    itemEntity = pagingItems[index]!!,
                    onClick = {
                        viewModel.onEvent(HomeEvent.PreviewCat(pagingItems[index]!!))
                    }
                )
            }
            pagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { Spacer(modifier = Modifier.padding(4.dp)) }
                        item { PageLoader(modifier = Modifier.fillMaxWidth()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = pagingItems.loadState.refresh as LoadState.Error
                        viewModel.onEvent(HomeEvent.ShowError(error.error.localizedMessage!!))
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = pagingItems.loadState.append as LoadState.Error
                        viewModel.onEvent(HomeEvent.ShowError(error.error.localizedMessage!!))
                    }
                }
            }
            item { Spacer(modifier = Modifier.padding(4.dp)) }
        }

        if (uiState.isError) {
            ErrorMessage(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                message = uiState.errorMsg,
                onClickRetry = {
                    viewModel.onEvent(HomeEvent.HideError)
                    pagingItems.retry()
                })
        }

    }
}
