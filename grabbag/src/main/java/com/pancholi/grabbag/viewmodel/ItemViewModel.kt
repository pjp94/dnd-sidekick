package com.pancholi.grabbag.viewmodel

import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.mapper.ItemMapper
import com.pancholi.grabbag.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val itemMapper: ItemMapper,
    private val dispatcher: Dispatcher
) : CategoryViewModel() {

    private val _viewState: MutableStateFlow<Result> = MutableStateFlow(Result.Loading)
    override val viewState: StateFlow<Result> = _viewState

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(dispatcher.io) {
            itemRepository
                .getAllItems()
                .distinctUntilChanged()
                .catch { _viewState.value = Result.Error(it) }
                .collect { entities ->
                    if (entities.isNotEmpty()) {
                        val items = entities.map { itemMapper.fromEntity(it) }
                        val viewState = ViewState(items = items)
                        _viewState.value = Result.Success(viewState)
                    } else {
                        _viewState.value = Result.Error(EmptyDatabaseException())
                    }
                }
        }
    }
}