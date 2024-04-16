package co.meteto.cat.presentation.detail

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.meteto.cat.presentation.util.resource.theme.colorBlack20
import co.meteto.cat.presentation.util.resource.theme.colorBlack80
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter


@Composable
fun DetailsScreen(viewModel: DetailViewModel = hiltViewModel()) {
    val cat by viewModel.catState.collectAsState()
    val painter = rememberAsyncImagePainter(cat.url)
    val transition by animateFloatAsState(
        targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
        label = "CatImage"
    )
    val ctx = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.effect.collect {
            when (it) {
                is DetailEffect.ShowToast -> Toast.makeText(
                    ctx, it.msg, Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(colorBlack80)
    ) {
        Card(
            Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.Center),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(Modifier.fillMaxSize()) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .alpha(transition)
                )

                Column(
                    Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                        .padding()
                ) {
                    Text(
                        text = cat.id,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                    Text(
                        text = "${cat.width},${cat.height}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Row(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.onEvent(DetailEvent.Message(cat)) }, Modifier
                            .background(Color.White, CircleShape)
                            .size(32.dp)
                    ) {
                        Image(imageVector = Icons.Filled.Email, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    IconButton(
                        onClick = { viewModel.onEvent(DetailEvent.Meeting(cat)) }, Modifier
                            .background(Color.White, CircleShape)
                            .size(32.dp)
                    ) {
                        Image(imageVector = Icons.Filled.Phone, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    IconButton(
                        onClick = { viewModel.onEvent(DetailEvent.BookMark(cat)) }, Modifier
                            .background(Color.White, CircleShape)
                            .size(32.dp)
                    ) {
                        Image(imageVector = Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            }
        }
    }
}
