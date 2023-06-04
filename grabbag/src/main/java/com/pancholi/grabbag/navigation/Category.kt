package com.pancholi.grabbag.navigation

import androidx.annotation.StringRes
import com.pancholi.grabbag.R

enum class Category(
    @StringRes val nameId: Int,
    val route: String
) {

    NPC(
        nameId = R.string.npcs,
        route = "npc_route"
    ),
    SHOP(
        nameId = R.string.shops,
        route = "shop_route"
    ),
    LOCATION(
        nameId = R.string.locations,
        route = "location_route"
    ),
    ITEM(
        nameId = R.string.items,
        route = "item_route"
    )
}