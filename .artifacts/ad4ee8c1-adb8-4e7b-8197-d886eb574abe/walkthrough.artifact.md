# Resumen de Mejoras en el Módulo UseCases

He completado la refactorización del módulo `:usecases`, mejorando la consistencia del código y aplicando patrones más idiomáticos de Kotlin.

## Cambios Realizados

### 1. Estandarización de Nomenclatura
- **Eliminación del prefijo 'i'**: Se han renombrado todas las propiedades privadas inyectadas en los constructores de los casos de uso (ej. de `iAppPreferences` a `appPreferences`). Esto alinea el código con las convenciones modernas de Kotlin donde el prefijo 'i' se reserva para las interfaces y no para las instancias.
- **Consistencia en Parámetros**: Se han revisado los nombres de los parámetros en las funciones para que sean más descriptivos y consistentes en todo el módulo.

### 2. Refactorización Idiomática en `AppUseCase`
- **Uso de `.any()`**: Se ha simplificado la función `validateBlocking` utilizando la función de extensión `.any()` de Kotlin. Esto hace que la lógica de validación de usuarios bloqueados sea mucho más legible y declarativa.
- **Limpieza de Lógica**: Se han eliminado redundancias y se ha mejorado el formato general del archivo.

### 3. Consolidación de Responsabilidades
- **Limpieza de `ConfigUseCase` y `DataUseCase`**: Se han eliminado espacios en blanco innecesarios y se ha mejorado la legibilidad de las llamadas a la capa de red/datos.
- **Revisión de `UserUseCase`**: Se ha mantenido la estructura actual pero con la nueva nomenclatura, asegurando que cada caso de uso tenga una interfaz limpia para los ViewModels.

## Verificación

- **Compilación Exitosa**: Se ha verificado con `gradlew :usecases:assembleDebug` y `gradlew :app:assembleDebug` que los cambios son compatibles con el resto del proyecto.
- **Hilt**: La inyección de dependencias sigue funcionando correctamente ya que solo se cambiaron nombres internos de propiedades privadas.

> [!TIP]
> Con estos cambios, tu capa de dominio es ahora más fácil de leer. Recuerda que al usar inyección de dependencias con Hilt, Kotlin asocia automáticamente los parámetros del constructor con las instancias proveídas, sin importar el nombre que le des a la propiedad interna.
