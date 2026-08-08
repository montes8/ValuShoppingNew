# Reglas para el módulo Repository

# Retrofit 2
-keepattributes Signature, InnerClasses, AnnotationDefault
-keepclassmembers,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Gson
-keepattributes Signature
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }

# Security Crypto (EncryptedSharedPreferences)
-keep class androidx.security.crypto.** { *; }
-dontwarn androidx.security.crypto.**
