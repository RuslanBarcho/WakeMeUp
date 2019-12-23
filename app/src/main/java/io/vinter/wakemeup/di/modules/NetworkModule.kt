package io.vinter.wakemeup.di.modules

import io.vinter.wakemeup.utils.AuthInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { RxJava2CallAdapterFactory.create() }
    single { GsonConverterFactory.create() }
    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get()))
                .build()
    }
    single {
        Retrofit.Builder()
                .client(get() as OkHttpClient)
                .addCallAdapterFactory(get() as RxJava2CallAdapterFactory)
                .addConverterFactory(get() as GsonConverterFactory)
                .baseUrl("http://95.165.154.234:3000/")
                .build()
    }
}