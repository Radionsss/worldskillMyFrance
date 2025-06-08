package workwork.company.worldskillstest

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import workwork.company.worldskillstest.domain.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import workwork.company.worldskillstest.navigation.Route
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {
    private val _startDestination = mutableStateOf<Route>(Route.SplashScreen)
    val startDestination: MutableState<Route> = _startDestination
    init {
        viewModelScope.launch {
            delay(1000)
            setDestination(Route.EventsListScreen)
        }
    }

    fun setDestination(route: Route){
        _startDestination.value = route
    }
}
