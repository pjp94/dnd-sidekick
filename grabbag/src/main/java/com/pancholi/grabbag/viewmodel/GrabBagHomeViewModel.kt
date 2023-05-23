package com.pancholi.grabbag.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.grabbag.CategoryScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GrabBagHomeViewModel @Inject constructor(
    private val dispatcher: Dispatcher
) : ViewModel() {

    companion object {

        fun provideFactory(
            dispatcher: Dispatcher
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return GrabBagHomeViewModel(dispatcher = dispatcher) as T
            }
        }
    }

    private val _navigationRoute = MutableSharedFlow<String>()
    val navigationRoute: SharedFlow<String> = _navigationRoute

    internal fun onCategoryClicked(
        category: CategoryScreen
    ) {
        viewModelScope.launch(dispatcher.main) {
            when (category) {
                is CategoryScreen.Npc -> _navigationRoute.emit(CategoryScreen.Npc.route)
                is CategoryScreen.Shop -> TODO()
                is CategoryScreen.Location -> TODO()
                is CategoryScreen.Item -> TODO()
            }
        }
    }

    fun onAddClicked() {

    }
}