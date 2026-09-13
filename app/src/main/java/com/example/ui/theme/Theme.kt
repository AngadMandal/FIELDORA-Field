package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val MedicalDarkColorScheme =
  darkColorScheme(
    primary = Color(0xFF00C853),
    onPrimary = Color.Black,
    secondary = Color(0xFF00B4D8),
    onSecondary = Color.Black,
    background = Color(0xFF0B132B),
    onBackground = Color(0xFFE2E8F0),
    surface = Color(0xFF1C2541),
    onSurface = Color(0xFFFFFFFF)
  )

private val MedicalLightColorScheme =
  lightColorScheme(
    primary = Color(0xFF0A2540), // Medical Navy
    onPrimary = Color.White,
    secondary = Color(0xFF00B4D8), // Teal Cyan
    onSecondary = Color.White,
    tertiary = Color(0xFF00C853), // Emerald Green
    background = Color(0xFFF4F7F6),
    onBackground = Color(0xFF0F172A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0F172A)
  )

val MedicalShapes = Shapes(
  extraSmall = RoundedCornerShape(4.dp),
  small = RoundedCornerShape(8.dp),
  medium = RoundedCornerShape(12.dp),
  large = RoundedCornerShape(16.dp),
  extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Use our custom medical-tech palette by default
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> MedicalDarkColorScheme
      else -> MedicalLightColorScheme
    }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    shapes = MedicalShapes,
    content = content
  )
}

