package screens.home;

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import models.ImagesResponse
import network.ApiStatus
import network.NetworkRepository


class HomeViewModel(private val networkRepository: NetworkRepository) {

    private val _homeViewState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState.Loading)
    val homeViewState = _homeViewState.asStateFlow()

    suspend fun getImages() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                networkRepository.getImages().collect { response ->
                    when (response.status) {
                        ApiStatus.LOADING -> {
                            _homeViewState.value = HomeScreenState.Loading
                        }

                        ApiStatus.SUCCESS -> {
                            response.data?.let {
                                _homeViewState.value = HomeScreenState.Success(it)
                            }
                        }

                        ApiStatus.ERROR -> {
                            _homeViewState.value = HomeScreenState.Error(response.message.orEmpty())
                        }
                    }
                }
            } catch (e: Exception) {
                _homeViewState.value = HomeScreenState.Error("Failed to fetch data")
            }
        }
    }

    sealed class HomeScreenState {
        data object Loading : HomeScreenState()
        data class Error(val errorMessage: String) : HomeScreenState()
        data class Success(val responseData: ImagesResponse) : HomeScreenState()
    }
}