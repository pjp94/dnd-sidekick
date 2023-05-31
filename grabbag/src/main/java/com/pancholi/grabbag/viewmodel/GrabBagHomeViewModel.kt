package com.pancholi.grabbag.viewmodel

import androidx.lifecycle.ViewModel
import com.pancholi.core.coroutines.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GrabBagHomeViewModel @Inject constructor(
    private val dispatcher: Dispatcher
) : ViewModel() {

    fun onAddClicked() {

    }
}