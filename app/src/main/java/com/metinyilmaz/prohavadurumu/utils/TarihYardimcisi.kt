package com.metinyilmaz.prohavadurumu.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Tarih/saat dönüşüm yardımcıları.
 */
object TarihYardimcisi {

    private val turkce = Locale("tr", "TR")

    /** Unix zaman damgasını "Saat:Dakika" formatına çevirir. */
    fun saateDonustur(unix: Long, zamanDilimi: Int = 0): String {
        val takvim = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            timeInMillis = (unix + zamanDilimi) * 1000L
        }
        return String.format(turkce, "%02d:%02d", takvim.get(Calendar.HOUR_OF_DAY), takvim.get(Calendar.MINUTE))
    }

    /** Unix zaman damgasını "Gün Ay Yıl" formatına çevirir. */
    fun tariheDonustur(unix: Long): String {
        val fmt = SimpleDateFormat("d MMMM yyyy", turkce)
        return fmt.format(Date(unix * 1000L))
    }

    /** Unix zaman damgasını "Pazartesi, 26 Mart" formatına çevirir. */
    fun gunVeTarih(unix: Long): String {
        val fmt = SimpleDateFormat("EEEE, d MMMM", turkce)
        return fmt.format(Date(unix * 1000L)).replaceFirstChar { it.uppercase(turkce) }
    }

    /** "yyyy-MM-dd HH:mm:ss" formatındaki metni "Sal 15:00" gibi kısa forma çevirir. */
    fun tahminZamani(zamanMetin: String): String {
        return try {
            val girdi = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            val cikis = SimpleDateFormat("EEE HH:mm", turkce)
            val tarih = girdi.parse(zamanMetin)
            tarih?.let { cikis.format(it).replaceFirstChar { c -> c.uppercase(turkce) } } ?: zamanMetin
        } catch (e: Exception) {
            zamanMetin
        }
    }
}
