package kz.butik.data.di

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.butik.data.data_source.local.LocalDataSource
import kz.butik.data.data_source.local.LocalDataSourceI
import kz.butik.data.data_source.local.NewsDao
import kz.butik.data.data_source.remote.RemoteDataSource
import kz.butik.data.data_source.remote.RemoteDataSourceI
import kz.butik.data.local_models.ArticleLocalModel
import kz.butik.data.repository.NewsRepository
import kz.butik.domain.repo.NewsRepo
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<RemoteDataSourceI> {
        RemoteDataSource(get())
    }

    single<NewsRepo> {
        NewsRepository(
            get(), get()
        )
    }

    single {
        Room.databaseBuilder(androidContext(), DataBase::class.java, "final_database").build()
    }

    single { get<DataBase>().newsDao() }

    single<LocalDataSourceI> {
        LocalDataSource(get())
    }


}

@Database(entities = [ArticleLocalModel::class], version = 1, exportSchema = false)
@TypeConverters(kz.butik.data.utils.TypeConverters::class)
abstract class DataBase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
