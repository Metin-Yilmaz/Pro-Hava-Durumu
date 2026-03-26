# Pro Hava Durumu 🌤️

Kotlin ile geliştirilmiş, modern Android hava durumu uygulaması.

## Özellikler

- 🔍 **Şehir arama** — herhangi bir şehrin hava durumunu anlık olarak sorgulayın
- 📍 **GPS konumu** — bulunduğunuz konumun hava durumunu otomatik alın
- 🌡️ **Anlık veriler** — sıcaklık, hissedilen, min/maks, nem, rüzgar, basınç, görüş mesafesi
- 📅 **5 günlük tahmin** — 3 saatlik adımlarla yatay kaydırmalı tahmin listesi
- 🌅 **Gün doğumu / batımı** saatleri
- 🇹🇷 **Tamamen Türkçe** — API yanıtları dahil

## Mimari

```
MVVM (Model – View – ViewModel)
├── api/         → Retrofit servisi, Repository, SonucDurumu sealed class
├── model/       → Gson veri sınıfları (HavaDurumuYaniti, TahminYaniti)
├── viewmodel/   → HavaDurumuViewModel (LiveData + Coroutines)
├── ui/          → MainActivity, TahminAdaptoru
└── utils/       → HavaYardimcisi, TarihYardimcisi
```

## Teknoloji Yığını

| Kütüphane | Versiyon | Amaç |
|---|---|---|
| Kotlin | 1.9.23 | Programlama dili |
| Retrofit | 2.11.0 | HTTP istemcisi |
| OkHttp Logging | 4.12.0 | Ağ günlüğü |
| Gson | (Retrofit ile) | JSON dönüşümü |
| Coroutines | 1.8.1 | Asenkron işlemler |
| LiveData / ViewModel | 2.8.1 | MVVM bileşenleri |
| Glide | 4.16.0 | İkon yükleme |
| Google Play Location | 21.3.0 | GPS konumu |
| Material Components | 1.12.0 | UI bileşenleri |

## Kurulum

### 1. API Anahtarı Alın
[OpenWeatherMap](https://openweathermap.org/api) sitesine kayıt olun ve ücretsiz bir API anahtarı edinin.

### 2. API Anahtarını Yapılandırın
Proje kök dizinindeki `local.properties` dosyasına ekleyin:

```properties
WEATHER_API_KEY=buraya_api_anahtarinizi_yazin
```

### 3. Projeyi Derleyin
```bash
./gradlew assembleDebug
```

## Ekran Görüntüleri

> Uygulama ilk çalıştırıldığında İstanbul hava durumunu yükler.

## Lisans

MIT License — Metin Yılmaz
