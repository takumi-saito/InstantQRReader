package com.kireaji.instantqrreader

import android.app.Application
import com.google.android.gms.instantapps.InstantApps

class MyApplication : Application() {

    val isInstantApp by lazy {
        InstantApps.getPackageManagerCompat(this).isInstantApp
        // true
    }
    override fun onCreate() {
        super.onCreate()
    }
}