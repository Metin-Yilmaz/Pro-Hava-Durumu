package com.metinyilmaz.prohavadurumu.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metinyilmaz.prohavadurumu.api.HavaDurumuDepo
import com.metinyilmaz.prohavadurumu.api.SonucDurumu
import com.metinyilmaz.prohavadurumu.model.HavaDurumuYaniti
import com.metinyilmaz.prohavadurumu.model.TahminYaniti
import kotlinx.coroutines.launch

/**
 * Ana ekranın ViewModel'i.
 * Hem anlık hava durumu hem de 5 günlük tahmini yönetir.
 */
class HavaDurumuViewModel : ViewModel() {

    private val depo = HavaDurumuDepo()

    private val _havaDurumu = MutableLiveData<SonucDurumu<HavaDurumuYaniti>>()
    val havaDurumu: LiveData<SonucDurumu<HavaDurumuYaniti>> = _havaDurumu

    private val _tahmin = MutableLiveData<SonucDurumu<TahminYaniti>>()
    val tahmin: LiveData<SonucDurumu<TahminYaniti>> = _tahmin

    private val _aramaSehri = MutableLiveData<String>()
    val aramaSehri: LiveData<String> = _aramaSehri

    /** Şehir adına göre hem anlık hem tahmin verilerini çeker. */
    fun sehirAra(sehirAdi: String) {
        if (sehirAdi.isBlank()) return
        _aramaSehri.value = sehirAdi
        sehireGoreHavaDurumu(sehirAdi)
        sehireGoreTahmin(sehirAdi)
    }

    /** GPS koordinatlarına göre hem anlık hem tahmin verilerini çeker. */
    fun konumaGoreAra(enlem: Double, boylam: Double) {
        koordinataGoreHavaDurumu(enlem, boylam)
        koordinataGoreTahmin(enlem, boylam)
    }

    private fun sehireGoreHavaDurumu(sehirAdi: String) {
        _havaDurumu.value = SonucDurumu.Yukleniyor
        viewModelScope.launch {
            _havaDurumu.value = depo.sehireGoreHavaDurumu(sehirAdi)
        }
    }

    private fun koordinataGoreHavaDurumu(enlem: Double, boylam: Double) {
        _havaDurumu.value = SonucDurumu.Yukleniyor
        viewModelScope.launch {
            _havaDurumu.value = depo.koordinataGoreHavaDurumu(enlem, boylam)
        }
    }

    private fun sehireGoreTahmin(sehirAdi: String) {
        _tahmin.value = SonucDurumu.Yukleniyor
        viewModelScope.launch {
            _tahmin.value = depo.sehireGoreTahmin(sehirAdi)
        }
    }

    private fun koordinataGoreTahmin(enlem: Double, boylam: Double) {
        _tahmin.value = SonucDurumu.Yukleniyor
        viewModelScope.launch {
            _tahmin.value = depo.koordinataGoreTahmin(enlem, boylam)
        }
    }
}
