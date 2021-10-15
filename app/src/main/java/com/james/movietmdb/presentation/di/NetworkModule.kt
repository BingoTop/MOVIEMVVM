package com.james.movietmdb.presentation.di

import com.james.movietmdb.BuildConfig
import com.james.movietmdb.data.utils.Constants
import com.james.movietmdb.data.api.DetailApiInterface
import com.james.movietmdb.data.api.ListApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient():OkHttpClient{
        OkHttpClient()
        return OkHttpClient.Builder()
            .readTimeout(5000L,TimeUnit.MILLISECONDS)
            .connectTimeout(5000L,TimeUnit.MILLISECONDS)
            .addInterceptor(object:Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request = chain.request()
                    val url = request.url.newBuilder().addQueryParameter(
                        Constants.API_KEY_QUERY,
                        BuildConfig.API_KEY).build()
                    request = request.newBuilder().url(url).build()
                    return chain.proceed(request)
                }
            })
            .build()


    }

    @Provides
    @Singleton
    fun provideListApi(retrofit: Retrofit): ListApiInterface {
        return retrofit.create(ListApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailApi(retrofit: Retrofit): DetailApiInterface {
        return retrofit.create(DetailApiInterface::class.java)
    }
}