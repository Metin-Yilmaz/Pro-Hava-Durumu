package com.metinyilmaz.prohavadurumu.api

/**
 * API sonucunu üç durumda temsil eder: yükleniyor, başarılı, hata.
 */
sealed class SonucDurumu<out T> {
    object Yukleniyor : SonucDurumu<Nothing>()
    data class Basarili<T>(val veri: T) : SonucDurumu<T>()
    data class Hata(val mesaj: String) : SonucDurumu<Nothing>()
}
