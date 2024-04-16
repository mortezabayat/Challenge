package co.meteto.cat.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import co.meteto.cat.core.app.AppPreferences
import co.meteto.cat.domain.model.Cat
import co.meteto.cat.presentation.home.HomeEffect
import co.meteto.cat.presentation.home.HomeEvent
import co.meteto.cat.presentation.home.HomeState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookmarksViewModel @Inject constructor(private val gson: Gson) : ViewModel() {

    private val _catsState: MutableStateFlow<Set<Cat>> = MutableStateFlow(value = emptySet())
    val catsState: MutableStateFlow<Set<Cat>> get() = _catsState

    init {
        getCats()
    }

    fun onEvent(event: BookmarksEvent) {
        viewModelScope.launch {
            when (event) {
                BookmarksEvent.GetBookmarks -> getCats()
                is BookmarksEvent.PreviewCat -> {
                    val catData = gson.toJson(event.cat)
                    AppPreferences.saveCatData(catData)
                    _effect.send(BookmarksEffect.NavigateDetails)
                }
            }
        }
    }

    private val _effect: Channel<BookmarksEffect> = Channel()
    val effect: Flow<BookmarksEffect> = _effect.receiveAsFlow()

    private fun getCats() {
        viewModelScope.launch {
            _catsState.value = AppPreferences.subscribe().toHashSet()
        }
    }
}

sealed class BookmarksEvent {
    data class PreviewCat(val cat: Cat) : BookmarksEvent()
    data object GetBookmarks : BookmarksEvent()
}

sealed interface BookmarksEffect {
    data object NavigateDetails : BookmarksEffect
}