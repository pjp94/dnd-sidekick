package com.pancholi.grabbag.ui.screen.item

import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.mapper.ItemMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.Item
import com.pancholi.grabbag.repository.ItemRepository
import com.pancholi.grabbag.viewmodel.CategoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _showRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showRequired: StateFlow<Boolean> = _showRequired.asStateFlow()

    private val _itemSaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val itemSaved: StateFlow<Boolean> = _itemSaved.asStateFlow()

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

    override fun onSaveClicked(model: CategoryModel) {
        val item = model as Item
        val requiredFields = listOf(item.name, item.type)

        if (areFieldsMissing(requiredFields)) {
            viewModelScope.launch { _showRequired.emit(true) }
        } else {
            val entity = itemMapper.toEntity(item)

            viewModelScope.launch(dispatcher.io) {
                itemRepository.insertItem(entity)
                _itemSaved.emit(true)
            }
        }
    }
}