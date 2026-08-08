# Plan de Centralización de Red: Eliminación total de Boilerplate

Este plan corrige la redundancia que mencionaste, moviendo la verificación de conectividad a un Interceptor y centralizando la ejecución de llamadas a la API para que las clases Network sean lo más limpias posible.

## User Review Required

> [!IMPORTANT]
> **Cambio de Flujo**: La verificación de internet ya no ocurrirá manualmente dentro de cada función. Ocurrirá automáticamente a nivel de red (OkHttp). Si no hay internet, se lanzará una `MyNetworkException` antes de que la petición siquiera intente salir.

## Proposed Changes

### 1. Interceptor de Conectividad Automático

#### [NEW] [ConnectivityInterceptor.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/repository/src/main/java/com/tayler/repository/network/interceptor/ConnectivityInterceptor.kt)
- Clase que hereda de `Interceptor`.
- Lógica de `isConnected()` e `isAirplaneModeActive()` centralizada aquí.

#### [MODIFY] [ConfigModule.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/repository/src/main/java/com/tayler/repository/di/ConfigModule.kt)
- Inyectar el `ConnectivityInterceptor` en el `OkHttpClient`.

### 2. Centralización de Llamadas (Safe Api Call)

#### [MODIFY] [BaseNetwork.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/repository/src/main/java/com/tayler/repository/network/base/BaseNerwork.kt)
- Crear una función genérica `safeApiCall` que envuelva el `try/catch` y el mapeo de excepciones (`toAppException`).

### 3. Limpieza de Clases Network

#### [MODIFY] [DataNetwork.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/repository/src/main/java/com/tayler/repository/network/api/DataNetwork.kt)
- Eliminar todos los `base.executeWithConnection`.
- Usar `base.safeApiCall` directamente.

#### [MODIFY] [UserNetwork.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/repository/src/main/java/com/tayler/repository/network/api/UserNetwork.kt) y [ConfigNetwork.kt](file:///Users/tayler/Desktop/project/android/ValuShoppingNew/repository/src/main/java/com/tayler/repository/network/api/ConfigNetwork.kt)
- Aplicar la misma simplificación drástica.

## Verification Plan

### Automated Tests
- Ejecutar `./gradlew :repository:assembleDebug`.
- Verificar que el `OkHttpClient` se construye correctamente con el nuevo interceptor.

### Manual Verification
- Desactivar el Wi-Fi/Datos y abrir la app. Se debe mostrar el diálogo de "Error de conexión" (esto confirma que el Interceptor funciona).
- Navegar con internet para confirmar que las llamadas exitosas siguen funcionando.
