package com.pancholi.grabbag.navigation

enum class Action(
    val route: String
) {

    ADD_NPC(
        route = "add_npc_route"
    ),
    EDIT_NPC(
        route = "edit_npc_route"
    ),
    ADD_SHOP(
        route = "add_shop_route"
    ),
    EDIT_SHOP(
        route = "edit_shop_route"
    ),
    ADD_LOCATION(
        route = "add_location_route"
    ),
    EDIT_LOCATION(
        route = "edit_location_route"
    ),
    ADD_ITEM(
        route = "add_item_route"
    ),
    EDIT_ITEM(
        route = "edit_item_route"
    )
}