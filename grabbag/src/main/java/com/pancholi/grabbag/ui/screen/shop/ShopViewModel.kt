package com.pancholi.grabbag.ui.screen.shop

import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.mapper.ShopMapper
import com.pancholi.grabbag.model.Shop
import com.pancholi.grabbag.repository.ShopRepository
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
class ShopViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val shopMapper: ShopMapper,
    private val dispatcher: Dispatcher
) : CategoryViewModel() {

    private val _viewState: MutableStateFlow<Result> = MutableStateFlow(Result.Loading)
    override val viewState: StateFlow<Result> = _viewState.asStateFlow()

    private val _showRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showRequired: StateFlow<Boolean> = _showRequired.asStateFlow()

    private val _shopSaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val shopSaved: StateFlow<Boolean> = _shopSaved.asStateFlow()

    private var name: String? = null
    private var type: String? = null
    private var owner: String? = null
    private var description: String? = null

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(dispatcher.io) {
            shopRepository
                .getAllShops()
                .distinctUntilChanged()
                .catch { _viewState.value = Result.Error(it) }
                .collect { entities ->
                    if (entities.isNotEmpty()) {
                        val shops = entities.map { shopMapper.fromEntity(it) }
                        val viewState = ViewState(items = shops)
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
            val shop = Shop(
                name = name.orEmpty(),
                type = type.orEmpty(),
                npcId = 0,
                description = description
            )
            val entity = shopMapper.toEntity(shop)

            viewModelScope.launch(dispatcher.io) {
                shopRepository
                    .insertShop(entity)

                _shopSaved.emit(true)
            }
        }
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setType(type: String) {
        this.type = type
    }

    fun setOwner(owner: String) {
        this.owner = owner
    }

    fun setDescription(description: String) {
        this.description = description
    }
}