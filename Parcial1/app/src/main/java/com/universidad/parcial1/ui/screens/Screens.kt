package com.universidad.parcial1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.universidad.parcial1.model.Nombres
import com.universidad.parcial1.model.listaNombres
import com.universidad.parcial1.navigation.Utils
import com.universidad.parcial1.ui.components.CardMode
import com.universidad.parcial1.ui.components.HeroCard
import com.universidad.parcial1.model.Bando
import com.universidad.parcial1.model.Universo
@Composable
fun ListaSuperHeroes(
    superheroes: List<Nombres>,
    favoritos: List<Int>,
    onVerDetalle: (Int) -> Unit,
    onToggleFavorito: (Int) -> Unit,
    onVerFavoritos: () -> Unit
) {
    var busqueda by rememberSaveable { mutableStateOf("") }
    var filtroBando by rememberSaveable { mutableStateOf<Bando?>(null) } // null = Todos
    var filtroUniverso by rememberSaveable { mutableStateOf<Universo?>(null) } // null = ambos
    val filtrados = superheroes.filter {
        it.nombre.contains(busqueda, ignoreCase = true) &&
                (filtroBando == null || it.bando == filtroBando) &&
                (filtroUniverso == null || it.universo == filtroUniverso)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                label = { Text("Buscar personaje") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Filtro héroes / villanos
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = filtroBando == null,
                    onClick = { filtroBando = null },
                    label = { Text("Todos") }
                )
                FilterChip(
                    selected = filtroBando == Bando.HEROE,
                    onClick = { filtroBando = Bando.HEROE },
                    label = { Text("Héroes") }
                )
                FilterChip(
                    selected = filtroBando == Bando.VILLANO,
                    onClick = { filtroBando = Bando.VILLANO },
                    label = { Text("Villanos") }
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Universo.values().forEach { universo ->
                    FilterChip(
                        selected = filtroUniverso == universo,
                        onClick = { filtroUniverso = if (filtroUniverso == universo) null else universo },
                        label = { Text(universo.etiqueta) }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mostrando ${filtrados.size} de ${superheroes.size} personajes",
                    style = MaterialTheme.typography.bodyMedium
                )
                FilledTonalButton(onClick = onVerFavoritos) {
                    Text("Favoritos (${favoritos.size})")
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (filtrados.isEmpty()) {
                item { Text("No se encontraron personajes con ese filtro.") }
            }
            items(items = filtrados, key = { it.id }) { heroe ->
                HeroCard(
                    hero = heroe,
                    mode = CardMode.COMPACT,
                    onClick = { onVerDetalle(heroe.id) },
                    esFavorito = heroe.id in favoritos,
                    onToggleFavorito = { onToggleFavorito(heroe.id) }
                )
            }
        }
    }
}

@Composable
fun DetalleSuperHeroe(
    hero: Nombres,
    esFavorito: Boolean,
    onToggleFavorito: () -> Unit,
    onVolver: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeroCard(
                hero = hero,
                mode = CardMode.DETAILED,
                esFavorito = esFavorito,
                onToggleFavorito = onToggleFavorito
            )
        }
        item {
            Button(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
                Text("Volver a la lista")
            }
        }
    }
}

// Reto 5: pantalla de favoritos
@Composable
fun PantallaFavoritos(
    favoritos: List<Nombres>,
    onVerDetalle: (Int) -> Unit,
    onToggleFavorito: (Int) -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Mis favoritos (${favoritos.size})", style = MaterialTheme.typography.headlineSmall)

        if (favoritos.isEmpty()) {
            Text("Aún no tienes favoritos. Toca el corazón de un héroe para agregarlo.")
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = favoritos, key = { it.id }) { heroe ->
                    HeroCard(
                        hero = heroe,
                        mode = CardMode.COMPACT,
                        onClick = { onVerDetalle(heroe.id) },
                        esFavorito = true,
                        onToggleFavorito = { onToggleFavorito(heroe.id) }
                    )
                }
            }
        }

        Button(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
            Text("Volver a la lista")
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val favoritos = remember { mutableStateListOf<Int>() }
    val toggleFavorito: (Int) -> Unit = { id ->
        if (id in favoritos) favoritos.remove(id) else favoritos.add(id)
    }

    NavHost(navController = navController, startDestination = Utils.Rutas.LISTA) {
        composable(Utils.Rutas.LISTA) {
            ListaSuperHeroes(
                superheroes = listaNombres,
                favoritos = favoritos,
                onVerDetalle = { id -> navController.navigate(Utils.Rutas.detalle(id)) },
                onToggleFavorito = toggleFavorito,
                onVerFavoritos = { navController.navigate(Utils.Rutas.FAVORITOS) }
            )
        }

        composable(Utils.Rutas.FAVORITOS) {
            PantallaFavoritos(
                favoritos = listaNombres.filter { it.id in favoritos },
                onVerDetalle = { id -> navController.navigate(Utils.Rutas.detalle(id)) },
                onToggleFavorito = toggleFavorito,
                onVolver = { navController.popBackStack() }
            )
        }

        composable(
            route = Utils.Rutas.DETALLE,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            val hero = listaNombres.firstOrNull { it.id == id } ?: return@composable

            DetalleSuperHeroe(
                hero = hero,
                esFavorito = hero.id in favoritos,
                onToggleFavorito = { toggleFavorito(hero.id) },
                onVolver = { navController.popBackStack() }
            )
        }
    }
}