package com.universidad.parcial1.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = RojoBoton,
    onPrimary = Blanco,
    primaryContainer = CajaSecundariaOscura,   // encabezado del detalle
    onPrimaryContainer = Purple80,             // texto lavanda del encabezado
    secondaryContainer = CajaSecundariaOscura, // "Resumen táctico"
    onSecondaryContainer = Blanco,
    background = Color(0xFF10141D),
    onBackground = Color.White,
    surface = Color(0xFF1B222C),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF252E3A),
    onSurfaceVariant = Color.White,
    outline = ChipOscuro
)

// ESQUEMA CLARO
private val LightColorScheme = lightColorScheme(
    primary = RojoMarvel,
    onPrimary = Blanco,
    secondary = AzulDC,
    onSecondary = Blanco,
    tertiary = AmarilloComic,
    background = FondoClaro,       // Importante: usar fondo claro aquí
    onBackground = TextoSobreClaro,
    surface = SuperficieClara,
    onSurface = TextoSobreClaro
)
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */


@Composable
fun Parcial1Theme(
    darkTheme: Boolean = true,
    // Cambia esto a false para que aplique TUS colores y no los del sistema
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}