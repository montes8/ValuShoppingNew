# 1. PROTECCIÓN DE MODELOS DE DATOS (Vital para Gson y Serialization)
# Estos no se pueden ofuscar porque se usan para convertir JSON
-keep class com.tayler.entity.** { *; }
-keep class com.tayler.valushopping.entity.** { *; }
-keep class com.tayler.repository.network.model.** { *; }
-keep class com.valu.uitaycompose.model.entity.** { *; }

# 2. ATRIBUTOS CRÍTICOS
-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod, SourceFile, LineNumberTable

# 3. GSON & REFLECTION
# Aunque usamos inline, mantenemos estas para mayor seguridad con listas
-keep class com.google.gson.** { *; }
-keep class java.lang.reflect.Type
-keep class java.lang.reflect.ParameterizedType
-keep class java.lang.reflect.GenericArrayType
-keep class java.lang.reflect.TypeVariable
-keep class java.lang.reflect.WildcardType
-keep class * extends com.google.gson.reflect.TypeToken

# 4. KOTLIN SERIALIZATION
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable *;
    kotlinx.serialization.KSerializer serializer(...);
}
-keep class **$serializer { *; }

# 5. RETROFIT
# Las interfaces se protegen para que Retrofit encuentre los métodos de la API
-keep interface * {
    @retrofit2.http.* <methods>;
}

# 6. EVITAR ADVERTENCIAS
-dontwarn sun.misc.**
-dontwarn javax.annotation.**
-dontwarn com.google.errorprone.annotations.**
