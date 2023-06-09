package com.pancholi.grabbag.navigation

import androidx.annotation.StringRes
import com.pancholi.grabbag.R

enum class Category(
    @StringRes val nameId: Int,
    val route: String,
    val addAction: Action,
    val editAction: Action
) {

    NPC(
        nameId = R.string.npcs,
        route = "npc_route",
        addAction = Action.ADD_NPC,
        editAction = Action.EDIT_NPC
    ),
    SHOP(
        nameId = R.string.shops,
        route = "shop_route",
        addAction = Action.ADD_SHOP,
        editAction = Action.EDIT_SHOP
    ),
    LOCATION(
        nameId = R.string.locations,
        route = "location_route",
        addAction = Action.ADD_LOCATION,
        editAction = Action.EDIT_LOCATION
    ),
    ITEM(
        nameId = R.string.items,
        route = "item_route",
        addAction = Action.ADD_ITEM,
        editAction = Action.EDIT_ITEM
    )
}