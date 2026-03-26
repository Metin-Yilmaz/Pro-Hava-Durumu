package com.metinyilmaz.prohavadurumu.model

import com.google.gson.annotations.SerializedName

/**
 * OpenWeatherMap 5 günlük / 3 saatlik tahmin API yanıtı.
 * API belgesi: https://openweathermap.org/forecast5
 */
data class TahminYaniti(
    @SerializedName("list") val tahminler: List<TahminOge>,
    @SerializedName("city") val sehir: SehirBilgisi
)

data class TahminOge(
    @SerializedName("dt") val zaman: Long,
    @SerializedName("main") val anaVeriler: AnaVeriler,
    @SerializedName("weather") val havaListesi: List<Hava>,
    @SerializedName("wind") val ruzgar: Ruzgar,
    @SerializedName("clouds") val bulutlar: Bulutlar,
    @SerializedName("pop") val yagisOlasiligi: Double,
    @SerializedName("dt_txt") val zamanMetin: String
)

data class SehirBilgisi(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val ad: String,
    @SerializedName("coord") val koordinat: Koordinat,
    @SerializedName("country") val ulke: String,
    @SerializedName("sunrise") val gunesDogumu: Long,
    @SerializedName("sunset") val gunBatimi: Long
)
