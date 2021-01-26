package id.dwichan.liongames

import android.app.Application
import id.dwichan.liongames.core.di.databaseModule
import id.dwichan.liongames.core.di.networkModule
import id.dwichan.liongames.core.di.repositoryModule
import id.dwichan.liongames.di.useCaseModule
import id.dwichan.liongames.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

}