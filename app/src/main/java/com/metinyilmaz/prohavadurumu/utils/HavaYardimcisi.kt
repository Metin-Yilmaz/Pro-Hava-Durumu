package com.metinyilmaz.prohavadurumu.utils

/**
 * Hava durumu ile ilgili dönüşüm ve yardımcı fonksiyonlar.
 */
object HavaYardimcisi {

    /**
     * OpenWeatherMap ikon kodunu tam URL'ye çevirir.
     * Örnek: "01d" → "https://openweathermap.org/img/wn/01d@2x.png"
     */
    fun ikonUrl(ikonKodu: String): String =
        "https://openweathermap.org/img/wn/$ikonKodu@2x.png"

    /**
     * Rüzgar derecesini Türkçe yön adına çevirir (K, KD, D, GD, G, GB, B, KB).
     */
    fun ruzgarYonu(derece: Int): String {
        val yonler = arrayOf("K", "KKD", "KD", "DKD", "D", "DGD", "GD", "GGD", "G", "GGB", "GB", "BGB", "B", "KBB", "KB", "KKB")
        val indeks = ((derece + 11.25) / 22.5).toInt() % 16
        return yonler[indeks]
    }

    /**
     * Görüş mesafesini metre'den km'ye çevirir ve formatlar.
     */
    fun gorusMesafesi(metre: Int?): String {
        if (metre == null) return "—"
        return if (metre >= 1000) "${metre / 1000} km" else "$metre m"
    }

    /**
     * Nem yüzdesine göre Türkçe açıklama döndürür.
     */
    fun nemAciklamasi(nem: Int): String = when {
        nem < 30 -> "Kuru"
        nem < 60 -> "Normal"
        nem < 80 -> "Nemli"
        else -> "Çok Nemli"
    }

    /**
     * Sıcaklık değerini "23°C" gibi biçimlendirir.
     */
    fun sicaklikMetni(sicaklik: Double): String =
        "${sicaklik.toInt()}°C"

    /**
     * Basınç değerini "1013 hPa" gibi biçimlendirir.
     */
    fun basincMetni(basinc: Int): String = "$basinc hPa"

    /**
     * Rüzgar hızını "15 km/s" gibi biçimlendirir (m/s → km/s).
     */
    fun ruzgarHiziMetni(hiz: Double): String =
        "${(hiz * 3.6).toInt()} km/s"
}
