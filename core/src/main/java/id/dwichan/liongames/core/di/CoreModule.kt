package id.dwichan.liongames.core.di

import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import id.dwichan.liongames.core.data.GamesRepository
import id.dwichan.liongames.core.data.source.local.LocalDataSource
import id.dwichan.liongames.core.data.source.local.room.GamesDatabase
import id.dwichan.liongames.core.data.source.remote.RemoteDataSource
import id.dwichan.liongames.core.data.source.remote.network.ApiService
import id.dwichan.liongames.core.domain.repository.IGameRepository
import id.dwichan.liongames.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<GamesDatabase>().gamesDao() }
    single {
        val password: ByteArray = SQLiteDatabase.getBytes("sebentar".toCharArray())
        val factory = SupportFactory(password)
        Room.databaseBuilder(
            androidContext(),
            GamesDatabase::class.java, "GamesDatabase.db"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val baseUrl = "api.rawg.io"
        val certificate = CertificatePinner.Builder()
            .add(baseUrl, "sha256/R+V29DqDnO269dFhAAB5jMlZHepWpDGuoejXJjprh7A=")
            .add(baseUrl, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
            .add(baseUrl, "sha256/Y9mvm0exBk1JoQ57f9Vm28jKo5lFm/woKcVxrYxu80o=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(androidContext())
                    .collector(ChuckerCollector(androidContext()))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificate)
            .build()
    }
    single {
        val baseUrl = "https://api.rawg.io/api/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IGameRepository> {
        GamesRepository(
            get(),
            get(),
            get()
        )
    }
}