package com.pancholi.dndsidekick.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.pancholi.dndsidekick.R

val SidekickFont = FontFamily(
        Font(R.font.taviraj_regular, FontWeight.Normal),
        Font(R.font.taviraj_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.taviraj_medium, FontWeight.Medium),
        Font(R.font.taviraj_mediumitalic, FontWeight.Medium, FontStyle.Italic),
        Font(R.font.taviraj_semibold, FontWeight.SemiBold),
        Font(R.font.taviraj_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
        Font(R.font.taviraj_bold, FontWeight.Bold),
        Font(R.font.taviraj_bolditalic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.taviraj_extrabold, FontWeight.Bold),
        Font(R.font.taviraj_extrabolditalic, FontWeight.Bold, FontStyle.Italic)
)

private val defaultTypography = Typography()
val Typography = Typography(
        displayLarge = defaultTypography.displayLarge.copy(fontFamily = SidekickFont),
        displayMedium = defaultTypography.displayMedium.copy(fontFamily = SidekickFont),
        displaySmall = defaultTypography.displaySmall.copy(fontFamily = SidekickFont),

        headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = SidekickFont),
        headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = SidekickFont),
        headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = SidekickFont),

        titleLarge = defaultTypography.titleLarge.copy(fontFamily = SidekickFont),
        titleMedium = defaultTypography.titleMedium.copy(fontFamily = SidekickFont),
        titleSmall = defaultTypography.titleSmall.copy(fontFamily = SidekickFont),

        bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = SidekickFont),
        bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = SidekickFont),
        bodySmall = defaultTypography.bodySmall.copy(fontFamily = SidekickFont),

        labelLarge = defaultTypography.labelLarge.copy(fontFamily = SidekickFont),
        labelMedium = defaultTypography.labelMedium.copy(fontFamily = SidekickFont),
        labelSmall = defaultTypography.labelSmall.copy(fontFamily = SidekickFont)
)