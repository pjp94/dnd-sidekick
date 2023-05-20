package com.pancholi.dndsidekick

import androidx.lifecycle.ViewModel
import com.pancholi.grabbag.database.GrabBagDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val database: GrabBagDatabase
) : ViewModel() {
}