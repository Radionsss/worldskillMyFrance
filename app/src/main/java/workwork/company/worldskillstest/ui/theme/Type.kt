package workwork.company.worldskillstest.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import workwork.company.worldskillstest.R

// Set of Material typography styles to start with
val mainFont = FontFamily(
    Font(R.font.main_font_open_sans_regular, FontWeight.Normal), // Regular
    Font(R.font.main_font_open_sans_semibold, FontWeight.SemiBold), // SemiBold
    Font(R.font.main_font_open_sans_medium, FontWeight.Medium), // SemiBold
    Font(R.font.main_font_open_sans_bold, FontWeight.Bold) // Bold
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
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