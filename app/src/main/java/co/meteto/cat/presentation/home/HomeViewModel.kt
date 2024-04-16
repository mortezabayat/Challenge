package co.meteto.cat.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.meteto.cat.core.app.AppPreferences
import co.meteto.cat.domain.model.Cat
import co.meteto.cat.domain.usecase.GetCatsUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCatsUseCase: GetCatsUseCase,
    private val gson: Gson
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeState> = MutableStateFlow(value = HomeState())
    private val _catsState: MutableStateFlow<PagingData<Cat>> =
        MutableStateFlow(value = PagingData.empty())
    val catsState: MutableStateFlow<PagingData<Cat>> get() = _catsState
    val uiState: MutableStateFlow<HomeState> get() = _uiState

    private val _effect: Channel<HomeEffect> = Channel()
    val effect: Flow<HomeEffect> = _effect.receiveAsFlow()

    init {
        onEvent(HomeEvent.GetHome)
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.GetHome -> {
                    getCats()
                }
                is HomeEvent.ShowError -> {
                    _uiState.value = HomeState(isError = true, errorMsg = event.msg)
                }
                HomeEvent.HideError -> {
                    _uiState.value = HomeState()
                }
                is HomeEvent.PreviewCat -> {
                    val catData = gson.toJson(event.cat)
                    AppPreferences.saveCatData(catData)
                    _effect.send(HomeEffect.NavigateDetails)
                }
            }
        }
    }

    private suspend fun getCats() {
        getCatsUseCase.execute(Unit)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _catsState.value = it
            }
    }
}

sealed class HomeEvent {
    data class ShowError(val msg: String) : HomeEvent()
    data class PreviewCat(val cat: Cat) : HomeEvent()
    data object HideError : HomeEvent()
    data object GetHome : HomeEvent()
}

data class HomeState(
    val isError: Boolean = false,
    val errorMsg: String = ""
)

sealed interface HomeEffect {
    data object NavigateDetails : HomeEffect
}