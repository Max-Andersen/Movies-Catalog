package com.example.moviecatalog.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R


val MyFontFamily = FontFamily(
    Font(resId = R.font.ibm_plex_sans_regular, FontWeight.W400, style = FontStyle.Normal)

)

val ibmRegular = FontFamily( Font(resId = R.font.ibm_plex_sans_regular, FontWeight.W400, style = FontStyle.Normal))
val ibmMedium = FontFamily(Font(resId = R.font.ibm_plex_sans_medium, FontWeight.W500, style = FontStyle.Normal))
val ibmBold = FontFamily(Font(resId = R.font.ibm_plex_sans_bold, FontWeight.W700, style = FontStyle.Normal))
val montserratMedium = FontFamily(Font(resId = R.font.montserrat_medium, FontWeight.W500, style = FontStyle.Normal))

// Set of Material typography styles to start with
val Typography = Typography(

    bodyLarge = TextStyle(    // for montserrat
        //fontWeight = FontWeight.Normal,
        fontFamily = montserratMedium,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        lineHeight = 14.63.sp,
        //letterSpacing = 0.5.sp
    ),

    headlineLarge = TextStyle( //H1
        fontFamily = ibmBold,
        //fontWeight = FontWeight.W700,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),

    headlineMedium = TextStyle( //H2
        //fontWeight = FontWeight.W700,
        fontFamily = ibmBold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),

    bodyMedium = TextStyle( //Body
        //fontWeight = FontWeight.W500,
        fontFamily = ibmMedium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),

    bodySmall = TextStyle( //Body Small
        //fontWeight = FontWeight.W400,
        fontFamily = ibmRegular,
        fontSize = 14.sp,
        lineHeight = 18.sp,
    ),

    titleLarge = TextStyle( //Title
        //fontWeight = FontWeight.W700,
        fontFamily = ibmBold,
        fontSize = 36.sp,
        lineHeight = 40.sp,
    ),

    labelSmall = TextStyle( //Footnote
        //fontWeight = FontWeight.W500,
        fontFamily = ibmMedium,
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