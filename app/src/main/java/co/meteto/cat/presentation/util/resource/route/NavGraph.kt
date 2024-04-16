package co.meteto.cat.presentation.util.resource.route

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.meteto.cat.R
import co.meteto.cat.core.app.AppPreferences
import co.meteto.cat.domain.model.Cat
import co.meteto.cat.presentation.bookmarks.BookmarksScreen
import co.meteto.cat.presentation.detail.DetailsScreen
import co.meteto.cat.presentation.home.HomeScreen
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val toolbarTitle = remember { mutableStateOf("") }

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {



            Text(
                text = toolbarTitle.value,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1.0f),
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = { navController.navigate(AppScreen.BookmarksScreen.route ,) },
                modifier = Modifier
                    .background(Color.White, CircleShape)
                    .size(32.dp)
            ) {
                Image(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    contentScale = ContentScale.Inside
                )
            }
        }
    }) { paddingValue ->


        Box(modifier = Modifier.padding(paddingValue)) {
            NavHost(
                navController = navController,
                startDestination = AppScreen.HomeScreen.route,
            ) {

                composable(route = AppScreen.HomeScreen.route) {
                    HomeScreen(
                        navController = navController
                    )
                    toolbarTitle.value = stringResource(id = R.string.spotLight)
                }

                composable(route = AppScreen.BookmarksScreen.route) {
                    BookmarksScreen(navController = navController)
                    toolbarTitle.value = stringResource(id = R.string.bookMarks)
                }

                // For Show Overlay We Used Dialog Routes
                dialog(route = AppScreen.DetailsScreen.route) {
                    BasicAlertDialog(
                        onDismissRequest = { navController.popBackStack() },
                        modifier = Modifier
                            .fillMaxSize(0.9f)
                            .padding(paddingValue),
                        properties = DialogProperties(
                            usePlatformDefaultWidth = false
                        ),
                        content = { DetailsScreen() }
                    )
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose { AppPreferences.onDispose() }
        }
    }
}