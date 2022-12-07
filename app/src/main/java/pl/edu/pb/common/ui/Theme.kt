package pl.edu.pb.common.ui

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Gray900,
    secondary = Rust600,
    background = Taupe100,
    surface = Color.White.copy(alpha = .85f),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Taupe800,
    onSurface = Gray900.copy(alpha = 0.8f)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    secondary = Rust300,
    background = Gray900,
    surface = Color.White.copy(alpha = 0.15f),
    onPrimary = Gray900,
    onSecondary = Gray900,
    onBackground = Taupe100,
    onSurface = Color.White.copy(alpha = .8f)
)

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = pickColorScheme(darkTheme)
    val view = LocalView.current

    if (!view.isInEditMode) {
        val currentWindow = (view.context as? Activity)?.window
            ?: error("Not in an activity - unable to get Window reference")

        SideEffect {
            currentWindow.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(currentWindow, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}

@Composable
fun pickColorScheme(darkTheme: Boolean): ColorScheme = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
}
