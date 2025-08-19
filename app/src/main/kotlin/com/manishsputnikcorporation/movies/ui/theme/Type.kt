package com.manishsputnikcorporation.movies.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.manishsputnikcorporation.movies.R

/** https://fonts.google.com/specimen/Montserrat */
private val montserrat =
    FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_medium, FontWeight.W500),
        Font(R.font.montserrat_semibold, FontWeight.W600),
    )

/** https://fonts.google.com/specimen/Domine */
private val domine = FontFamily(Font(R.font.domine_regular), Font(R.font.domine_bold, FontWeight.Bold))

val MoviesTypography =
    Typography(
        h4 = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Normal, fontSize = 34.sp),
        h5 = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Normal, fontSize = 24.sp),
        h6 = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Medium, fontSize = 20.sp),
        subtitle1 = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.W600, fontSize = 16.sp),
        subtitle2 = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.W500, fontSize = 14.sp),
        body1 = TextStyle(fontFamily = domine, fontWeight = FontWeight.Normal, fontSize = 16.sp),
        body2 = TextStyle(fontFamily = montserrat, fontSize = 14.sp),
        button = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.W500, fontSize = 14.sp),
        caption = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.Normal, fontSize = 12.sp),
        overline = TextStyle(fontFamily = montserrat, fontWeight = FontWeight.W500, fontSize = 12.sp),
    )
