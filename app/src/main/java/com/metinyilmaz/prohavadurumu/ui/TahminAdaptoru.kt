package com.metinyilmaz.prohavadurumu.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.metinyilmaz.prohavadurumu.databinding.ItemTahminBinding
import com.metinyilmaz.prohavadurumu.model.TahminOge
import com.metinyilmaz.prohavadurumu.utils.HavaYardimcisi
import com.metinyilmaz.prohavadurumu.utils.TarihYardimcisi

/**
 * 5 günlük tahmin listesi için RecyclerView adaptörü.
 */
class TahminAdaptoru : ListAdapter<TahminOge, TahminAdaptoru.TahminViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TahminViewHolder {
        val binding = ItemTahminBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TahminViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TahminViewHolder, position: Int) {
        holder.bagla(getItem(position))
    }

    inner class TahminViewHolder(
        private val binding: ItemTahminBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bagla(tahmin: TahminOge) {
            val hava = tahmin.havaListesi.firstOrNull()
            binding.apply {
                tvTahminZaman.text = TarihYardimcisi.tahminZamani(tahmin.zamanMetin)
                tvTahminSicaklik.text = HavaYardimcisi.sicaklikMetni(tahmin.anaVeriler.sicaklik)
                tvTahminAciklama.text = hava?.aciklama?.replaceFirstChar { it.uppercase() } ?: ""
                tvTahminNem.text = "%${tahmin.anaVeriler.nem}"
                tvTahminYagis.text = "%${(tahmin.yagisOlasiligi * 100).toInt()}"
                hava?.let {
                    Glide.with(ivTahminIkon.context)
                        .load(HavaYardimcisi.ikonUrl(it.ikon))
                        .into(ivTahminIkon)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TahminOge>() {
            override fun areItemsTheSame(eski: TahminOge, yeni: TahminOge) =
                eski.zaman == yeni.zaman

            override fun areContentsTheSame(eski: TahminOge, yeni: TahminOge) =
                eski == yeni
        }
    }
}
