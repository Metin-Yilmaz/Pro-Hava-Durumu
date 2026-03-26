package com.metinyilmaz.prohavadurumu.api

import com.metinyilmaz.prohavadurumu.BuildConfig
import com.metinyilmaz.prohavadurumu.model.HavaDurumuYaniti
import com.metinyilmaz.prohavadurumu.model.TahminYaniti

/**
 * API çağrılarını saran depo katmanı.
 * ViewModel'ler bu sınıf üzerinden veri çeker.
 */
class HavaDurumuDepo {

    private val api = RetrofitIstemcisi.api
    private val apiAnahtari = BuildConfig.WEATHER_API_KEY

    suspend fun sehireGoreHavaDurumu(sehirAdi: String): SonucDurumu<HavaDurumuYaniti> {
        return try {
            val yanit = api.sehireGoreHavaDurumu(sehirAdi, apiAnahtari)
            if (yanit.isSuccessful) {
                yanit.body()?.let { SonucDurumu.Basarili(it) }
                    ?: SonucDurumu.Hata("Boş yanıt alındı")
            } else {
                SonucDurumu.Hata(yanit.message() ?: "Bilinmeyen hata")
            }
        } catch (e: Exception) {
            SonucDurumu.Hata(e.localizedMessage ?: "Bağlantı hatası")
        }
    }

    suspend fun koordinataGoreHavaDurumu(enlem: Double, boylam: Double): SonucDurumu<HavaDurumuYaniti> {
        return try {
            val yanit = api.koordinataGoreHavaDurumu(enlem, boylam, apiAnahtari)
            if (yanit.isSuccessful) {
                yanit.body()?.let { SonucDurumu.Basarili(it) }
                    ?: SonucDurumu.Hata("Boş yanıt alındı")
            } else {
                SonucDurumu.Hata(yanit.message() ?: "Bilinmeyen hata")
            }
        } catch (e: Exception) {
            SonucDurumu.Hata(e.localizedMessage ?: "Bağlantı hatası")
        }
    }

    suspend fun sehireGoreTahmin(sehirAdi: String): SonucDurumu<TahminYaniti> {
        return try {
            val yanit = api.sehireGoreTahmin(sehirAdi, apiAnahtari)
            if (yanit.isSuccessful) {
                yanit.body()?.let { SonucDurumu.Basarili(it) }
                    ?: SonucDurumu.Hata("Boş yanıt alındı")
            } else {
                SonucDurumu.Hata(yanit.message() ?: "Bilinmeyen hata")
            }
        } catch (e: Exception) {
            SonucDurumu.Hata(e.localizedMessage ?: "Bağlantı hatası")
        }
    }

    suspend fun koordinataGoreTahmin(enlem: Double, boylam: Double): SonucDurumu<TahminYaniti> {
        return try {
            val yanit = api.koordinataGoreTahmin(enlem, boylam, apiAnahtari)
            if (yanit.isSuccessful) {
                yanit.body()?.let { SonucDurumu.Basarili(it) }
                    ?: SonucDurumu.Hata("Boş yanıt alındı")
            } else {
                SonucDurumu.Hata(yanit.message() ?: "Bilinmeyen hata")
            }
        } catch (e: Exception) {
            SonucDurumu.Hata(e.localizedMessage ?: "Bağlantı hatası")
        }
    }
}
