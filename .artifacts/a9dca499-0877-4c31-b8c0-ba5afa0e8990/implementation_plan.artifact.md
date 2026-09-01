# Plan de Modularización - Fase 5: Eliminación de Módulos Legacy

Este plan detalla los pasos finales para eliminar los módulos `:repository` y `:usecases`, moviendo la lógica global restante a módulos de Core o Features específicas.

## User Review Required

> [!CAUTION]
> Esta es la fase final de limpieza. Se eliminarán físicamente las carpetas `repository/` y `usecases/`. Asegúrate de no tener cambios locales sin guardar en esos módulos.

## Proposed Changes

### [Componente: Core Network]
Absorción de la lógica de red global.

#### [MOVE] Protocolos e Implementaciones
- Mover `IConfigNetwork`, `IUserNetwork`, `IDataNetwork` y sus implementaciones a `core:network`.
- Mover el DTO `TaskResponse.kt` a `core:network`.

#### [MODIFY] [NetworkModule.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/core/network/src/main/java/com/tayler/core/network/di/NetworkModule.kt)
- Incluir los `@Binds` para los nuevos repositorios globales movidos.

---

### [Componente: Core Domain (NUEVO)]
Creación de un módulo para lógica de negocio compartida u orquestadora.

#### [NEW] Módulo :core:domain
- Crear módulo `:core:domain` dependiente de `:core:network`, `:core:database` y `:core:model`.
- Mover `AppUseCase.kt`, `ConfigUseCase.kt` y `DataUseCase.kt` a este módulo.

---

### [Componente: App / Splash]
Actualización de los puntos de entrada.

#### [MODIFY] [AppViewModel](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/app/src/main/java/com/tayler/valushopping/ui/splash/AppViewModel.kt) y [ApplicationVale](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/app/src/main/java/com/tayler/valushopping/application/ApplicationVale.kt)
- Actualizar los imports para apuntar a `:core:domain`.

---

### [Limpieza Final]
#### [DELETE] Módulos Legacy
- Eliminar `repository/` del sistema de archivos.
- Eliminar `usecases/` del sistema de archivos.
- Quitar `include(":repository")` e `include(":usecases")` de `settings.gradle.kts`.

## Verification Plan

### Automated Tests
- `./gradlew clean assembleDebug` para confirmar que todo el grafo se reconstruye sin los módulos eliminados.

### Manual Verification
- Iniciar la app desde cero y verificar que el Splash y el Handshake cuántico funcionen correctamente.
