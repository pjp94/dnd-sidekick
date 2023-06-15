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
import com.pancholi.grabbag.repository.ItemRepository
import com.pancholi.grabbag.ui.screen.CategoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(dispatcher.io) {
            itemRepository
                .getAllItems()
                .distinctUntilChanged()
                .catch { onResultChanged(Result.Error(it)) }
                .collect { entities ->
                    val result = if (entities.isNotEmpty()) {
                        val items = entities.map { itemMapper.fromEntity(it) }
                        Result.Success(items)
                    } else {
                        Result.Error(EmptyDatabaseException())
                    }

                    onResultChanged(result)
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