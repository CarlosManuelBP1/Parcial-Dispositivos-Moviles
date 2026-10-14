package com.universidad.parcial1.model

enum class Universo(val etiqueta: String) {
    MARVEL("Marvel"),
    DC("DC")

}
enum class Bando(val etiqueta: String) {
    HEROE("Héroe"),
    VILLANO("Villano")
}

data class Nombres(
    val id: Int,
    val nombre: String,
    val imagenUrl: String,
    val descripcion: String,
    val superPoderes: List<String>,
    val universo: Universo,
    val bando: Bando = Bando.HEROE
)

val listaNombres = listOf(
    Nombres(
        id = 1,
        nombre = "Superman",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/644-superman.jpg",
        descripcion = "Superman es un héroe reconocido por ACTION COMICS #1.",
        superPoderes = listOf("Inteligencia superior", "Super fuerza", "Super velocidad", "Alta resistencia"),
        universo = Universo.DC
    ),
    Nombres(
        id = 2,
        nombre = "Batman",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/70-batman.jpg",
        descripcion = "El Caballero de la Noche, un vigilante de Gotham City.",
        superPoderes = listOf("Intelecto nivel genio", "Artes marciales", "Riqueza", "Gadgets"),
        universo = Universo.DC
    ),
    Nombres(
        id = 3,
        nombre = "Wonder Woman",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/720-wonder-woman.jpg",
        descripcion = "Princesa amazona de Themyscira y guerrera de la Liga de la Justicia.",
        superPoderes = listOf("Super fuerza", "Combate", "Vuelo", "Lazo de la verdad"),
        universo = Universo.DC
    ),
    Nombres(
        id = 4,
        nombre = "Flash",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/263-flash.jpg",
        descripcion = "El hombre más rápido del mundo.",
        superPoderes = listOf("Super velocidad", "Reflejos", "Curación rápida"),
        universo = Universo.DC
    ),
    Nombres(
        id = 5,
        nombre = "Spider-Man",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/620-spider-man.jpg",
        descripcion = "El vecino y amigable Hombre Araña de Nueva York.",
        superPoderes = listOf("Sentido arácnido", "Agilidad", "Trepar paredes", "Super fuerza"),
        universo = Universo.MARVEL
    ),
    Nombres(
        id = 6,
        nombre = "Iron Man",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/346-iron-man.jpg",
        descripcion = "Tony Stark, genio e inventor que se protege con una armadura propia.",
        superPoderes = listOf("Inteligencia superior", "Armadura de combate", "Riqueza", "Gadgets"),
        universo = Universo.MARVEL
    ),
    Nombres(
        id = 7,
        nombre = "Thor",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/659-thor.jpg",
        descripcion = "Dios del trueno de Asgard.",
        superPoderes = listOf("Control del trueno", "Super fuerza", "Energía cósmica", "Vuelo"),
        universo = Universo.MARVEL
    ),
    Nombres(
        id = 8,
        nombre = "Hulk",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/332-hulk.jpg",
        descripcion = "El doctor Bruce Banner transformado en una furia verde.",
        superPoderes = listOf("Fuerza descomunal", "Regeneración", "Resistencia extrema"),
        universo = Universo.MARVEL
    ),Nombres(
        id = 9,
        nombre = "Joker",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/370-joker.jpg",
        descripcion = "El Príncipe Payaso del Crimen, eterno enemigo de Batman en Gotham.",
        superPoderes = listOf("Intelecto criminal", "Artes marciales", "Impredecibilidad", "Gadgets letales"),
        universo = Universo.DC,
        bando = Bando.VILLANO
    ),
    Nombres(
        id = 10,
        nombre = "Thanos",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/655-thanos.jpg",
        descripcion = "El Titán Loco que busca equilibrar el universo con las Gemas del Infinito.",
        superPoderes = listOf("Super fuerza", "Energía cósmica", "Inmortalidad", "Guantelete del Infinito"),
        universo = Universo.MARVEL,
        bando = Bando.VILLANO
    ),
    Nombres(
        id = 11,
        nombre = "Loki",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/414-loki.jpg",
        descripcion = "Dios del engaño de Asgard y hermano adoptivo de Thor.",
        superPoderes = listOf("Inteligencia superior", "Magia", "Ilusiones", "Combate con dagas"),
        universo = Universo.MARVEL,
        bando = Bando.VILLANO
    ),
    Nombres(
        id = 12,
        nombre = "Darkseid",
        imagenUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/204-darkseid.jpg",
        descripcion = "Tirano de Apokolips que busca la Ecuación Anti-Vida.",
        superPoderes = listOf("Super fuerza", "Rayos Omega", "Energía cósmica", "Inmortalidad"),
        universo = Universo.DC,
        bando = Bando.VILLANO
    )

)