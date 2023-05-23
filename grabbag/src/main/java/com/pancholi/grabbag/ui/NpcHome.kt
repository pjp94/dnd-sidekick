package com.pancholi.grabbag.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pancholi.core.Result
import com.pancholi.grabbag.viewmodel.NpcViewModel

internal const val ROUTE = "npc_home"

@Composable
internal fun NpcHome(
    navHostController: NavHostController,
    viewModel: NpcViewModel = hiltViewModel()
) {
//    val state = viewModel.viewState.collectAsStateWithLifecycle()
//    Log.d("NPC_HOME_TAG", state.value.toString())
//
//    NpcContent(npcState = state)
    Log.d("NPC_HOME_TAG", "NpcHome being drawn")
    LoadingIndicator()
}

@Composable
internal fun NpcContent(
    npcState: State<Result>
) {
    when (npcState.value) {
        is Result.Loading -> LoadingIndicator()
        is Result.Success<*> -> {
            val viewState = (npcState.value as Result.Success<*>).value as ViewState<*>

            if (viewState.items.isNotEmpty()) {

            } else {
                viewState.categoryNameId?.let { EmptyMessage(categoryNameId = it) }
            }
        }
        is Result.Error -> {}
    }
}

@Composable
internal fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}