package com.pancholi.grabbag

import androidx.annotation.StringRes

sealed class CategoryScreen(
    @StringRes val nameId: Int,
    val route: String
) {

    object Npc : CategoryScreen(
        nameId = R.string.npcs,
        route = "npc_route"
    )

    object Shop : CategoryScreen(
        nameId = R.string.shops,
        route = "shop_route"
    )

    object Location : CategoryScreen(
        nameId = R.string.locations,
        route = "location_route"
    )

    object Item : CategoryScreen(
        nameId = R.string.items,
        route = "item_route"
    )
}