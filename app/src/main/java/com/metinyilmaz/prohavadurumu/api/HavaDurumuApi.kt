package com.metinyilmaz.prohavadurumu.api

import com.metinyilmaz.prohavadurumu.model.HavaDurumuYaniti
import com.metinyilmaz.prohavadurumu.model.TahminYaniti
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * OpenWeatherMap REST API tanımları.
 * Tüm istekler Türkçe yanıt (lang=tr) ve metrik birimler (units=metric) ile yapılır.
 */
interface HavaDurumuApi {

    /**
     * Şehir adına göre anlık hava durumu.
     */
    @GET("weather")
    suspend fun sehireGoreHavaDurumu(
        @Query("q") sehirAdi: String,
        @Query("appid") apiAnahtari: String,
        @Query("units") birim: String = "metric",
        @Query("lang") dil: String = "tr"
    ): Response<HavaDurumuYaniti>

    /**
     * Koordinatlara göre anlık hava durumu.
     */
    @GET("weather")
    suspend fun koordinataGoreHavaDurumu(
        @Query("lat") enlem: Double,
        @Query("lon") boylam: Double,
        @Query("appid") apiAnahtari: String,
        @Query("units") birim: String = "metric",
        @Query("lang") dil: String = "tr"
    ): Response<HavaDurumuYaniti>

    /**
     * 5 günlük / 3 saatlik hava tahmini (şehir adına göre).
     */
    @GET("forecast")
    suspend fun sehireGoreTahmin(
        @Query("q") sehirAdi: String,
        @Query("appid") apiAnahtari: String,
        @Query("units") birim: String = "metric",
        @Query("lang") dil: String = "tr"
    ): Response<TahminYaniti>

    /**
     * 5 günlük / 3 saatlik hava tahmini (koordinatlara göre).
     */
    @GET("forecast")
    suspend fun koordinataGoreTahmin(
        @Query("lat") enlem: Double,
        @Query("lon") boylam: Double,
        @Query("appid") apiAnahtari: String,
        @Query("units") birim: String = "metric",
        @Query("lang") dil: String = "tr"
    ): Response<TahminYaniti>
}
