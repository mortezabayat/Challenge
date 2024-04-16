package co.meteto.cat.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import co.meteto.cat.core.app.AppPreferences
import co.meteto.cat.domain.model.Cat
import co.meteto.cat.presentation.home.HomeEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor() : ViewModel() {

    private val _catState: MutableStateFlow<Cat> = MutableStateFlow(value = Cat())
    val catState: MutableStateFlow<Cat> get() = _catState
    private val _effect: Channel<DetailEffect> = Channel()
    val effect: Flow<DetailEffect> = _effect.receiveAsFlow()

    init {
        onEvent(DetailEvent.GetDetail)
    }

    fun onEvent(event: DetailEvent) {
        viewModelScope.launch {
            when (event) {
                is DetailEvent.BookMark -> {
                    AppPreferences.saveBookMark(cat = event.cat)
                }

                DetailEvent.GetDetail -> getCat()
                is DetailEvent.Meeting -> {
                    _effect.send(DetailEffect.ShowToast("Meeting is not Implemented"))
                }

                is DetailEvent.Message -> {
                    _effect.send(DetailEffect.ShowToast("Message is not Implemented"))
                }
            }

        }
    }

    private fun getCat() {

        viewModelScope.launch {
            _catState.value = AppPreferences.getCatData()
        }
    }
}

sealed class DetailEvent {
    data object GetDetail : DetailEvent()
    data class BookMark(val cat: Cat) : DetailEvent()
    data class Message(val cat: Cat) : DetailEvent()
    data class Meeting(val cat: Cat) : DetailEvent()
}

sealed interface DetailEffect {

    data class ShowToast(val msg: String) : DetailEffect

}
