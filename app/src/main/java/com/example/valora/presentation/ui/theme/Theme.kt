package com.example.valora.presentation.ui.theme


import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = NebulaPurple,
    secondary = CosmicPink,
    tertiary = LightPurple,
    background = DeepBlue,
    surface = DeepBlue,
    onPrimary = StarlightSilver,
    onSecondary = StarlightSilver,
    onTertiary = StarlightSilver,
    onBackground = StarlightSilver,
    onSurface = StarlightSilver,
    surfaceVariant = GlassyColor,
    error = Color(0xFFCF6679),
    onError = Color.Black
)

@Composable
fun GalaxyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme // We are forcing a dark, galaxy theme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
