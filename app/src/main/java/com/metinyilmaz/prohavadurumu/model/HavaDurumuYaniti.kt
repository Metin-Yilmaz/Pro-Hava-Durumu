package com.metinyilmaz.prohavadurumu.model

import com.google.gson.annotations.SerializedName

/**
 * OpenWeatherMap API'sinden gelen anlık hava durumu verisi.
 * API belgesi: https://openweathermap.org/current
 */
data class HavaDurumuYaniti(
    @SerializedName("coord") val koordinat: Koordinat,
    @SerializedName("weather") val havaListesi: List<Hava>,
    @SerializedName("main") val anaVeriler: AnaVeriler,
    @SerializedName("wind") val ruzgar: Ruzgar,
    @SerializedName("clouds") val bulutlar: Bulutlar,
    @SerializedName("visibility") val gorusKm: Int?,
    @SerializedName("dt") val olcumZamani: Long,
    @SerializedName("sys") val sistem: Sistem,
    @SerializedName("timezone") val zamanDilimi: Int,
    @SerializedName("id") val sehirId: Int,
    @SerializedName("name") val sehirAdi: String,
    @SerializedName("cod") val durmKodu: Int
)

data class Koordinat(
    @SerializedName("lon") val boylam: Double,
    @SerializedName("lat") val enlem: Double
)

data class Hava(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val ana: String,
    @SerializedName("description") val aciklama: String,
    @SerializedName("icon") val ikon: String
)

data class AnaVeriler(
    @SerializedName("temp") val sicaklik: Double,
    @SerializedName("feels_like") val hissedilen: Double,
    @SerializedName("temp_min") val minSicaklik: Double,
    @SerializedName("temp_max") val maxSicaklik: Double,
    @SerializedName("pressure") val basınc: Int,
    @SerializedName("humidity") val nem: Int
)

data class Ruzgar(
    @SerializedName("speed") val hiz: Double,
    @SerializedName("deg") val yon: Int,
    @SerializedName("gust") val hamle: Double?
)

data class Bulutlar(
    @SerializedName("all") val yuzde: Int
)

data class Sistem(
    @SerializedName("country") val ulke: String,
    @SerializedName("sunrise") val gunes: Long,
    @SerializedName("sunset") val gunbatimi: Long
)
