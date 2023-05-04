package com.nakyoung.navermap

import android.app.Application
import com.naver.maps.map.NaverMapSdk

class MatravelApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient("up7w4uu36d")
    }
}