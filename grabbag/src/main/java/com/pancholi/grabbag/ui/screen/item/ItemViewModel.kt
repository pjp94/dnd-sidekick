package com.pancholi.grabbag.ui.screen.item

import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.mapper.ItemMapper
import com.pancholi.grabbag.model.Currency
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

    private val _viewState: MutableStateFlow<Result> = MutableStateFlow(Result.Loading)
    override val viewState: StateFlow<Result> = _viewState

    private val _showRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showRequired: StateFlow<Boolean> = _showRequired.asStateFlow()

    private val _itemSaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val itemSaved: StateFlow<Boolean> = _itemSaved.asStateFlow()

    private var name: String? = null
    private var type: String? = null
    private var cost: String? = null
    private var currency: Currency = Currency.GP
    private var description: String? = null

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

    override fun onSaveClicked() {
        if (areFieldsMissing(listOf(name, type))) {
            viewModelScope.launch { _showRequired.emit(true) }
        } else {
            val item = Item(
                name = name.orEmpty(),
                type = type.orEmpty(),
                cost = cost,
                currency = currency,
                description = description
            )
            val entity = itemMapper.toEntity(item)

            viewModelScope.launch(dispatcher.io) {
                itemRepository
                    .insertItem(entity)

                _itemSaved.emit(true)
            }
        }
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setType(type: String) {
        this.type = type
    }

    fun setCost(cost: String) {
        this.cost = cost
    }

    fun setCurrency(currency: Currency) {
        this.currency = currency
    }

    fun setDescription(description: String) {
        this.description = description
    }
}