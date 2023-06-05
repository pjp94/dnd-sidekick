package com.pancholi.dndsidekick.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.pancholi.dndsidekick.R

enum class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    @StringRes val labelId: Int,
    @DrawableRes val iconId: Int,
    @StringRes val iconDescriptionId: Int
) {

    GRAB_BAG(
        route = "grab_bag_home",
        resourceId = R.string.grab_bag_route,
        labelId = R.string.grab_bag_label,
        iconId = R.drawable.bag,
        iconDescriptionId = R.string.grab_bag_icon_description
    ),
    BATTLE_TRACKER(
        route = "battle_tracker_home",
        resourceId = R.string.battle_tracker_route,
        labelId = R.string.battle_tracker_label,
        iconId = R.drawable.crossed_swords,
        iconDescriptionId = R.string.battle_tracker_icon_description
    )
}