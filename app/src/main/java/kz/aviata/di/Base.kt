package kz.aviata.di

import kz.aviata.base.BaseResourcesManager
import kz.aviata.base.ResourcesManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val baseModule = module {
    single<BaseResourcesManager> { ResourcesManager(androidContext()) }
}
