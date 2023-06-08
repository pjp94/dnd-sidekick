package com.pancholi.grabbag.ui.screen.item

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.ItemMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
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
    private val dispatcher: Dispatcher,
    private val resources: Resources
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
                .catch { _viewState.emit(Result.Error(it)) }
                .collect { entities ->
                    if (entities.isNotEmpty()) {
                        val items = entities.map { itemMapper.fromEntity(it) }
                        val viewState = ViewState(items = items)
                        _viewState.emit(Result.Success(viewState))
                    } else {
                        _viewState.emit(Result.Error(EmptyDatabaseException()))
                    }
                }
        }
    }

    override fun onSaveClicked(model: CategoryModel) {
        val item = model as CategoryModel.Item
        val requiredFields = listOf(item.name, item.type)

        if (areFieldsMissing(requiredFields)) {
            viewModelScope.launch { _showRequired.emit(true) }
        } else {
            val entity = itemMapper.toEntity(item)

            viewModelScope.launch(dispatcher.io) {
                itemRepository.insertItem(entity)
                _itemSaved.emit(true)
            }

            val visuals = SidekickSnackbarVisuals(
                message = resources.getString(R.string.item_saved)
            )

            showSnackbar(visuals)
        }
    }

    override fun onModelsImported(models: List<ImportedModel>?) {
        models?.let {
            val items = models.map { itemMapper.toEntity(it as ImportedModel.ImportedItem) }

            viewModelScope.launch(dispatcher.io) {
                itemRepository.insertAllItems(items)
            }
        }
    }

    override fun onDeleteModel(model: CategoryModel) {
        val item = model as CategoryModel.Item
        val entity = itemMapper.toEntity(item)

        viewModelScope.launch(dispatcher.io) {
            val deleted = itemRepository.deleteItem(entity)

            if (deleted > 0) {
                val visuals = SidekickSnackbarVisuals(
                    message = resources.getString(R.string.item_deleted)
                )

                showSnackbar(visuals)
            }
        }
    }
}