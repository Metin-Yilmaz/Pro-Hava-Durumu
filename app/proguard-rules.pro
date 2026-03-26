# ProGuard kuralları — Pro Hava Durumu uygulaması

# Retrofit model sınıflarını koru
-keep class com.metinyilmaz.prohavadurumu.model.** { *; }

# Retrofit arayüzlerini koru
-keep interface com.metinyilmaz.prohavadurumu.api.HavaDurumuApi { *; }

# Gson için gerekli kurallar
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
