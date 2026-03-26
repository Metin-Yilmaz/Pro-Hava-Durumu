package com.metinyilmaz.prohavadurumu.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.metinyilmaz.prohavadurumu.api.SonucDurumu
import com.metinyilmaz.prohavadurumu.databinding.ActivityMainBinding
import com.metinyilmaz.prohavadurumu.utils.HavaYardimcisi
import com.metinyilmaz.prohavadurumu.utils.TarihYardimcisi
import com.metinyilmaz.prohavadurumu.viewmodel.HavaDurumuViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: HavaDurumuViewModel by viewModels()
    private lateinit var konumIstemcisi: FusedLocationProviderClient
    private val tahminAdaptoru = TahminAdaptoru()

    private val konumIzniLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { izinler ->
        val verildi = izinler[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                izinler[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (verildi) mevcutKonumuGetir()
        else Toast.makeText(this, "Konum izni verilmedi", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        konumIstemcisi = LocationServices.getFusedLocationProviderClient(this)

        recyclerViewAyarla()
        aramaKutusuAyarla()
        konumButonuAyarla()
        gozlemleriBaslat()

        // Uygulama açılışında İstanbul'u varsayılan şehir olarak yükle
        viewModel.sehirAra("Istanbul")
    }

    private fun recyclerViewAyarla() {
        binding.rvTahmin.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL, false
            )
            adapter = tahminAdaptoru
        }
    }

    private fun aramaKutusuAyarla() {
        binding.etSehirAra.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val sehir = binding.etSehirAra.text.toString().trim()
                if (sehir.isNotEmpty()) viewModel.sehirAra(sehir)
                true
            } else false
        }

        binding.btnAra.setOnClickListener {
            val sehir = binding.etSehirAra.text.toString().trim()
            if (sehir.isNotEmpty()) viewModel.sehirAra(sehir)
            else Toast.makeText(this, "Lütfen bir şehir adı girin", Toast.LENGTH_SHORT).show()
        }
    }

    private fun konumButonuAyarla() {
        binding.btnKonum.setOnClickListener {
            konumIzniKontrolEt()
        }
    }

    private fun konumIzniKontrolEt() {
        val ince = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val kaba = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (ince == PackageManager.PERMISSION_GRANTED || kaba == PackageManager.PERMISSION_GRANTED) {
            mevcutKonumuGetir()
        } else {
            konumIzniLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    private fun mevcutKonumuGetir() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) return

        konumIstemcisi.lastLocation.addOnSuccessListener { konum ->
            konum?.let {
                viewModel.konumaGoreAra(it.latitude, it.longitude)
            } ?: Toast.makeText(this, "Konum bulunamadı", Toast.LENGTH_SHORT).show()
        }
    }

    private fun gozlemleriBaslat() {
        viewModel.havaDurumu.observe(this) { durum ->
            when (durum) {
                is SonucDurumu.Yukleniyor -> yukleniyorGoster()
                is SonucDurumu.Basarili -> havaDurumuGoster(durum.veri)
                is SonucDurumu.Hata -> hataGoster(durum.mesaj)
            }
        }

        viewModel.tahmin.observe(this) { durum ->
            when (durum) {
                is SonucDurumu.Basarili -> tahminAdaptoru.submitList(durum.veri.tahminler)
                else -> Unit
            }
        }
    }

    private fun yukleniyorGoster() {
        binding.progressBar.visibility = View.VISIBLE
        binding.layoutIcerik.visibility = View.GONE
        binding.tvHata.visibility = View.GONE
    }

    private fun havaDurumuGoster(veri: com.metinyilmaz.prohavadurumu.model.HavaDurumuYaniti) {
        binding.progressBar.visibility = View.GONE
        binding.tvHata.visibility = View.GONE
        binding.layoutIcerik.visibility = View.VISIBLE

        val hava = veri.havaListesi.firstOrNull()

        binding.apply {
            tvSehirAdi.text = "${veri.sehirAdi}, ${veri.sistem.ulke}"
            tvTarih.text = TarihYardimcisi.gunVeTarih(veri.olcumZamani)
            tvSicaklik.text = HavaYardimcisi.sicaklikMetni(veri.anaVeriler.sicaklik)
            tvHissedilen.text = "Hissedilen: ${HavaYardimcisi.sicaklikMetni(veri.anaVeriler.hissedilen)}"
            tvAciklama.text = hava?.aciklama?.replaceFirstChar { it.uppercase() } ?: ""
            tvMinMax.text = "↓ ${HavaYardimcisi.sicaklikMetni(veri.anaVeriler.minSicaklik)}  ↑ ${HavaYardimcisi.sicaklikMetni(veri.anaVeriler.maxSicaklik)}"

            // Detay kartları
            tvNemDeger.text = "%${veri.anaVeriler.nem}"
            tvNemAciklama.text = HavaYardimcisi.nemAciklamasi(veri.anaVeriler.nem)
            tvRuzgarDeger.text = HavaYardimcisi.ruzgarHiziMetni(veri.ruzgar.hiz)
            tvRuzgarYon.text = HavaYardimcisi.ruzgarYonu(veri.ruzgar.yon)
            tvBasincDeger.text = HavaYardimcisi.basincMetni(veri.anaVeriler.basınc)
            tvGorisDeger.text = HavaYardimcisi.gorusMesafesi(veri.gorusKm)
            tvGunDogumu.text = TarihYardimcisi.saateDonustur(veri.sistem.gunes, veri.zamanDilimi)
            tvGunBatimi.text = TarihYardimcisi.saateDonustur(veri.sistem.gunbatimi, veri.zamanDilimi)

            hava?.let {
                Glide.with(this@MainActivity)
                    .load(HavaYardimcisi.ikonUrl(it.ikon))
                    .into(ivHavaIkon)
            }
        }
    }

    private fun hataGoster(mesaj: String) {
        binding.progressBar.visibility = View.GONE
        binding.layoutIcerik.visibility = View.GONE
        binding.tvHata.visibility = View.VISIBLE
        binding.tvHata.text = "Hata: $mesaj"
    }
}
