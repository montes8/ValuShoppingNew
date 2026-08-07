# Plan de Mejora del Módulo App

Este plan detalla las refactorizaciones recomendadas para mejorar la arquitectura, estabilidad y mantenibilidad del módulo `:app`. El objetivo es aplicar mejores prácticas de Android (Clean Architecture, Hilt, Jetpack Compose) sin alterar la funcionalidad actual.

## User Review Required

> [!IMPORTANT]
> **Cambio en AppDataVale**: Al convertir `AppDataVale` de un objeto estático a una clase inyectada, eliminaremos el acceso global `AppDataVale.variable`. En su lugar, se inyectará en los ViewModels y se pasará a los Composables (o se usará un `CompositionLocal`). Esto requiere cambios en muchos archivos del módulo `:app`.

> [!WARNING]
> **BaseViewModel**: Cambiaremos cómo se maneja el estado de carga y error para evitar conflictos entre diferentes ViewModels, eliminando el uso de `companion object`.

## Proposed Changes

### 1. Refactorización de Estado Global (`AppDataVale`)

Convertiremos `AppDataVale` en un administrador de estado inyectable para mejorar la testabilidad y evitar fugas de memoria.

#### [MODIFY] [AppDataVale.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/app/src/main/java/com/tayler/valushopping/entity/AppDataVale.kt)
- Cambiar de `object` a `class`.
- Agregar anotación `@Singleton` (vía un módulo de Hilt).
- Usar `StateFlow` para datos que cambian (como `paramData` o `user`) para que la UI reaccione automáticamente.

#### [NEW] [AppModule.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/app/src/main/java/com/tayler/valushopping/di/AppModule.kt)
- Crear módulo de Hilt para proveer la instancia única de `AppDataVale`.

---

### 2. Mejora en `BaseViewModel` y `BaseActivity`

Eliminar el estado compartido global por uno más seguro.

#### [MODIFY] [BaseViewModel.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/app/src/main/java/com/tayler/valushopping/ui/base/BaseViewModel.kt)
- Eliminar el `companion object`.
- Cada ViewModel tendrá su propio `_uiStateBase`.
- (Opcional) Crear un `GlobalEventManager` si se desea mantener la funcionalidad de errores globales.

#### [MODIFY] [BaseActivity.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/app/src/main/java/com/tayler/valushopping/ui/base/BaseActivity.kt)
- Ajustar la observación del estado para que provenga del ViewModel específico de la actividad.

---

### 3. Refactorización de `ScreenHome`

Dividir el archivo masivo en componentes manejables.

#### [MODIFY] [ScreenHome.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/app/src/main/java/com/tayler/valushopping/ui/home/ScreenHome.kt)
- Extraer `HomeDrawerContent`, `HomeBottomBar` y `HomeTopBar` a funciones o archivos separados.

---

### 4. Optimización de Corrutinas

#### [MODIFY] [InitActivity.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/app/src/main/java/com/tayler/valushopping/ui/InitActivity.kt)
- Limpiar el uso de `lifecycleScope` y `withContext` para que sea más idiomático y fácil de seguir.

---

### 5. Navegación y Usos de AppDataVale

#### [MODIFY] Múltiples Archivos en `:app`
- Actualizar todas las referencias de `AppDataVale.xxx` por la instancia inyectada o pasada por parámetro.

## Verification Plan

### Automated Tests
- Ejecutar `gradlew :app:assembleDebug` para asegurar que no hay errores de compilación tras los cambios de inyección.

### Manual Verification
- Iniciar la aplicación y verificar que el Splash carga correctamente.
- Navegar por el Home y comprobar que el estilo (colores) se aplica según `AppDataVale`.
- Abrir el Drawer y verificar las acciones de soporte y perfil.
- Forzar un error (ej. desconectando internet) para ver si el diálogo de error de `BaseActivity` sigue funcionando.
