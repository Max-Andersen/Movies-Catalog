package com.example.moviecatalog.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R


val MyFontFamily = FontFamily(
        Font(R.font.ibm_plex_sans_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(

    bodyLarge = TextStyle(    //default
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    headlineLarge = TextStyle(    //H1
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),

    headlineMedium = TextStyle(    //H2
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),

    bodyMedium = TextStyle(       //Body
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),

    bodySmall = TextStyle(        //Body Small
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 18.sp,
    ),

    titleLarge = TextStyle(         //Title
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 36.sp,
        lineHeight = 40.sp,
    ),

    labelSmall = TextStyle(         //Footnote
        fontFamily = MyFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 16.sp,
    ),


    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)