package io.smily.rxjavadigitas

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class RxJavaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}