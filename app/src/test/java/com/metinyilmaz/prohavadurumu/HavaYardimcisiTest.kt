package com.metinyilmaz.prohavadurumu

import com.metinyilmaz.prohavadurumu.utils.HavaYardimcisi
import org.junit.Test
import org.junit.Assert.*

class HavaYardimcisiTest {

    @Test
    fun ruzgarYonu_kuzey_K_dondurmeli() {
        assertEquals("K", HavaYardimcisi.ruzgarYonu(0))
        assertEquals("K", HavaYardimcisi.ruzgarYonu(360))
    }

    @Test
    fun ruzgarYonu_guney_G_dondurmeli() {
        assertEquals("G", HavaYardimcisi.ruzgarYonu(180))
    }

    @Test
    fun sicaklikMetni_dogru_format() {
        assertEquals("23°C", HavaYardimcisi.sicaklikMetni(23.7))
        assertEquals("-5°C", HavaYardimcisi.sicaklikMetni(-5.2))
    }

    @Test
    fun ikonUrl_dogru_url_dondurmeli() {
        assertEquals(
            "https://openweathermap.org/img/wn/01d@2x.png",
            HavaYardimcisi.ikonUrl("01d")
        )
    }
}

