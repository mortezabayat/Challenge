package co.meteto.cat.presentation.bookmarks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import co.meteto.cat.domain.model.Cat
import co.meteto.cat.presentation.home.HomeEvent
import co.meteto.cat.presentation.home.component.ItemCat
import co.meteto.cat.presentation.util.ErrorMessage
import co.meteto.cat.presentation.util.LoadingNextPageItem
import co.meteto.cat.presentation.util.PageLoader
import co.meteto.cat.presentation.util.resource.route.AppScreen


@Composable
fun BookmarksScreen(navController: NavController, viewModel: BookmarksViewModel = hiltViewModel()) {

    LaunchedEffect(viewModel) {
        viewModel.effect.collect {
            when(it){
                BookmarksEffect.NavigateDetails -> navController.navigate(AppScreen.DetailsScreen.route)
            }
        }
    }
    val items  by viewModel.catsState.collectAsState()
    Box(Modifier.fillMaxSize()) {

        if (items.isEmpty()){
            Text(text = "Empty List" , modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onPrimary)
        }else{
            LazyVerticalGrid(GridCells.Fixed(3)) {
                items(items.count()) { index ->
                    ItemCat(
                        itemEntity = items.elementAt(index),
                        onClick = {
                            viewModel.onEvent(BookmarksEvent.PreviewCat(items.elementAt(index)))
                        }
                    )
                }
                item { Spacer(modifier = Modifier.padding(4.dp)) }
            }
        }
    }
}