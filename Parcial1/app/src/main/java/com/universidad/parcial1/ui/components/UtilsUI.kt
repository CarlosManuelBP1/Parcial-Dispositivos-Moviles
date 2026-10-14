package com.universidad.parcial1.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.universidad.parcial1.model.Nombres
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.Color
import com.universidad.parcial1.model.Universo
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.luminance
// MEJORA: Un Enum es más seguro y legible que usar option = 0 o option = 1
enum class CardMode {
    COMPACT, DETAILED
}

@Composable
fun HeroImage(imageUrl: String, contentDescription: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier.fillMaxWidth().height(220.dp),
        contentScale = ContentScale.Crop,
        placeholder = ColorPainter(Color.LightGray),
        error = ColorPainter(Color.DarkGray)
    )
}

@Composable
fun HeroInfo(hero: Nombres, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "ID: ${hero.id}", style = MaterialTheme.typography.labelMedium)
        Text(text = hero.nombre, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 4.dp))
        Text(text = hero.descripcion, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 8.dp))
        Text(text = "Superpoderes: ${hero.superPoderes.joinToString()}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 8.dp))
        Text(text = "${hero.universo.etiqueta} · ${hero.bando.etiqueta}", style = MaterialTheme.typography.labelMedium)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HeroInfoDetail(hero: Nombres, modifier: Modifier = Modifier) {
    val perfil = heroProfile(hero.superPoderes)

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Encabezado fuerte: avatar con inicial + ID + nombre
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)

            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = hero.nombre.firstOrNull()?.uppercase() ?: "?",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Column {
                    Text(text = "ID: ${hero.id} · ${hero.bando.etiqueta}", style = MaterialTheme.typography.labelMedium)
                    Text(text = hero.nombre, style = MaterialTheme.typography.headlineMedium)
                }
            }
        }

        // 2. Descripción amplia
        Text(text = hero.descripcion, style = MaterialTheme.typography.bodyLarge)

        // 3. Métricas derivadas
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MetricBox(
                label = "Superpoderes",
                value = hero.superPoderes.size.toString(),
                modifier = Modifier.weight(1f)
            )
            MetricBox(
                label = "Perfil",
                value = perfil,
                modifier = Modifier.weight(1f)

            )
            MetricBox(
                label = "Universo",
                value = hero.universo.etiqueta,
                modifier = Modifier.weight(1f))
        }

        // 4. Superpoderes como chips
        Text(text = "Superpoderes", style = MaterialTheme.typography.titleMedium)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            hero.superPoderes.forEach { poder ->
                AssistChip(onClick = { }, label = { Text(poder) })
            }
        }

        // 5. Resumen táctico
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Resumen táctico", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${hero.nombre} tiene perfil $perfil y domina ${hero.superPoderes.size} habilidades, " +
                            "destacando ${hero.superPoderes.firstOrNull() ?: "ninguna"}.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun MetricBox(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, style = MaterialTheme.typography.titleMedium, maxLines = 1)
            Text(text = label, style = MaterialTheme.typography.labelMedium)
        }
    }
}
private fun heroProfile(superPoderes: List<String>): String = when {
    superPoderes.any { it.contains("Intelecto", ignoreCase = true) || it.contains("Inteligencia", ignoreCase = true) } &&
            superPoderes.any { it.contains("Combate", ignoreCase = true) || it.contains("Artes marciales", ignoreCase = true) } -> "Estratega"
    superPoderes.any { it.contains("Energía", ignoreCase = true) } -> "Cósmico"
    superPoderes.any { it.contains("velocidad", ignoreCase = true) } -> "Velocista"
    else -> "Titán"
}

@Composable
private fun colorUniverso(universo: Universo): Color {
    val oscuro = MaterialTheme.colorScheme.surface.luminance() < 0.5f
    return when (universo) {
        Universo.MARVEL -> if (oscuro) Color(0xFF2E1E24) else Color(0xFFFDE3E3)
        Universo.DC -> if (oscuro) Color(0xFF1B2638) else Color(0xFFDDE9FF)
    }
}

@Composable
fun HeroCard(
    hero: Nombres,
    mode: CardMode = CardMode.COMPACT,
    onClick: (() -> Unit)? = null,
    esFavorito: Boolean = false,
    onToggleFavorito: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorUniverso(hero.universo),
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
    ) {
        Box {
            HeroImage(imageUrl = hero.imagenUrl, contentDescription = hero.nombre)
            if (onToggleFavorito != null) {
                Surface(
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.45f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    IconButton(onClick = onToggleFavorito) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = if (esFavorito) "Quitar de favoritos" else "Agregar a favoritos",
                            tint = if (esFavorito) Color.Red else Color.White
                        )
                    }
                }
            }
        }
        when (mode) {
            CardMode.COMPACT -> HeroInfo(hero = hero)
            CardMode.DETAILED -> HeroInfoDetail(hero = hero)
        }
    }
}