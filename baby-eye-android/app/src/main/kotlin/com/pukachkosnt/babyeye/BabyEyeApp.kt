package com.pukachkosnt.babyeye

import android.app.Application
import com.pukachkosnt.babyeye.di.AppComponent
import com.pukachkosnt.babyeye.di.DaggerAppComponent

class BabyEyeApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}
