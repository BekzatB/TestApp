package kz.aviata

import android.app.Application
import kz.aviata.di.*
import kz.aviata.di.baseModule
import kz.aviata.di.domainModule
import kz.aviata.di.presentationModule
import kz.butik.data.di.dataModule
import kz.butik.data.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(retrofitModule, domainModule, dataModule, presentationModule, baseModule)
            )
        }
    }
}