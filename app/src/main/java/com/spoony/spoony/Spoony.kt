package com.spoony.spoony

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import coil3.ImageLoader
import coil3.SingletonImageLoader
import com.kakao.sdk.common.KakaoSdk
import com.spoony.spoony.domain.repository.TokenRepository
import dagger.Lazy
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import timber.log.Timber

@HiltAndroidApp
class Spoony : Application() {
    @Inject
    lateinit var tokenRepository: TokenRepository

    @Inject
    lateinit var imageLoader: Lazy<ImageLoader>

    override fun onCreate() {
        super.onCreate()
        SingletonImageLoader.setSafe { imageLoader.get() }

        initTimber()
        setDayMode()
        initKakaoSdk()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setDayMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}
