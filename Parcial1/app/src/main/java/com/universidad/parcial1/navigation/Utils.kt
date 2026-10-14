package com.universidad.parcial1.navigation

object Utils {
    object Rutas {
        const val LISTA = "lista"
        const val DETALLE = "detalle/{id}"

        const val FAVORITOS = "favoritos"
        fun detalle(id: Int) = "detalle/$id"
    }
}