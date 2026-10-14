# 🦸‍♂️ Marvel & DC Heroes App

Aplicación Android académica desarrollada con **Kotlin**, **Jetpack Compose** y **Material 3**. Permite explorar 12 personajes de Marvel y DC, consultar sus detalles, buscar y filtrar resultados, y guardar favoritos.

**Autor:** Carlos Manuel Botero Patiño  
**Universidad:** Universidad de Caldas  
**Paquete:** `com.universidad.parcial1`

## ✨ Funcionalidades

- **Catálogo de personajes:** lista desplazable implementada con `LazyColumn`.
- **Detalle enriquecido:** descripción, métricas, superpoderes y perfil táctico.
- **Búsqueda y filtros combinables:** por nombre, bando (héroes/villanos) y universo (Marvel/DC).
- **Favoritos:** permite marcar personajes y verlos en una pantalla independiente.
- **Interfaz adaptable:** tarjetas con colores según el universo y soporte para tema claro/oscuro.
- **Navegación:** rutas para lista, detalle y favoritos mediante Navigation Compose.
- **Imágenes remotas:** carga con Coil y muestra una imagen de respaldo si falla la descarga.

## 🧰 Tecnologías

| Área | Tecnología |
| --- | --- |
| Lenguaje | Kotlin |
| Interfaz | Jetpack Compose + Material 3 |
| Navegación | Navigation Compose 2.8.0 |
| Imágenes | Coil Compose 2.6.0 |
| Datos | Lista local de 12 personajes |
| Imágenes de personajes | Repositorio público `akabab/superhero-api` |

## 🦸 Personajes incluidos

| Universo | Héroes | Villanos |
| --- | --- | --- |
| **DC** | Superman, Batman, Wonder Woman, Flash | Joker, Darkseid |
| **Marvel** | Spider-Man, Iron Man, Thor, Hulk | Thanos, Loki |

## 🗂️ Estructura del proyecto

```text
app/src/main/java/com/universidad/parcial1/
├── MainActivity.kt          # Tema, Scaffold y navegación principal
├── model/
│   └── Nombres.kt           # Modelo, enums y lista de personajes
├── navigation/
│   └── Utils.kt             # Rutas de navegación
└── ui/
    ├── components/
    │   └── UtilsUI.kt       # Componentes reutilizables y tarjetas
    ├── screens/
    │   └── Screens.kt       # Lista, detalle, favoritos y navegación
    └── theme/               # Colores, tema y tipografía
```

## 🚀 Cómo ejecutar

1. Clona o descarga el repositorio y ábrelo en **Android Studio**.
2. Espera a que finalice la sincronización de Gradle.
3. Selecciona un emulador o dispositivo Android con conexión a internet.
4. Pulsa **Run** para compilar y ejecutar la aplicación.

Las dependencias principales se configuran en `gradle/libs.versions.toml` y `app/build.gradle.kts`:

```kotlin
implementation(libs.androidx.navigation.compose)
implementation(libs.coil.compose)
```

Para cargar imágenes remotas, `AndroidManifest.xml` debe incluir:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## 📸 Capturas

Agrega capturas reales de la aplicación en una carpeta como `capturas/` y enlázalas aquí. Se recomienda incluir:

- Lista de personajes con búsqueda y filtros.
- Pantalla de detalle de un héroe y de un villano.
- Pantalla de favoritos.

Ejemplo de enlace en Markdown:

```markdown
![Lista de personajes](capturas/lista-personajes.png)
```

## 🧩 Decisiones de diseño

- Los enums `Universo`, `Bando` y `CardMode` hacen que el código sea más claro y reducen errores frente a valores de texto o números sin significado.
- `HeroImage`, `HeroCard` y otros componentes reutilizables evitan duplicar código de interfaz.
- El estado de favoritos se comparte entre pantallas mediante parámetros y callbacks.
- El perfil táctico se calcula a partir de los superpoderes del personaje.

## ⚠️ Limitaciones conocidas

- Los favoritos se mantienen en memoria y se pierden al cerrar la aplicación; podrían persistirse en el futuro con `DataStore` o un `ViewModel` junto con almacenamiento persistente.
- El catálogo es una lista local fija; no se consulta una API para obtener los datos.
- La carga de imágenes requiere internet. Si una imagen no está disponible, se muestra un elemento de respaldo.

---

*Proyecto académico basado en la guía Playground de superhéroes (lista y detalle con Jetpack Compose).*
