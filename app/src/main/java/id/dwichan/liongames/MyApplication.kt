package id.dwichan.liongames

import android.app.Application
import id.dwichan.liongames.core.di.CoreComponent
import id.dwichan.liongames.core.di.DaggerCoreComponent
import id.dwichan.liongames.di.AppComponent
import id.dwichan.liongames.di.DaggerAppComponent

open class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}