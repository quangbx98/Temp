package com.fsoc.template.common.di.module

import android.content.Context
import com.fsoc.template.BuildConfig
import com.fsoc.template.common.AppCommon
import com.fsoc.template.data.api.BaseApi
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.*


@Module
class ApiModule(private val context: Context) {

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesOkHttpClient(
        @Named(BuildConfig.BUILD_TYPE) okHttpClientBuilder: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        httpHeaderInterceptor: Interceptor
    ): OkHttpClient {
        okHttpClientBuilder.addInterceptor(httpHeaderInterceptor)

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)
        }

        // optional
//        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
//        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
//        okHttpClientBuilder.writeTimeout(20, TimeUnit.SECONDS)

        return okHttpClientBuilder.build()
    }

    @Provides
    @Named("release")
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Provides
    @Named("debug")
    @Singleton
    fun provideOkHttpClientBuilderDebug(): OkHttpClient.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                    Logger.d("checkClientTrusted: $authType")
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                    Logger.d("checkServerTrusted: $authType")
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.getSocketFactory()

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)

            builder.hostnameVerifier(object : HostnameVerifier {
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    Logger.d("hostname: $hostname")
                    hostname?.let {
                        if (BuildConfig.BASE_URL.contains(hostname)) {
                            return true
                        }
                    }

                    return false
                }
            })

            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun provideHttpHeaderInterceptor() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            // fake token
            val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTU5MjMwMDk4NywidXNlcklkIjo0LCJ1dWlkIjoiMzg0MzdhNzE0MzVhNTk2MjRkNjU2MjU4NDczMjM2Mzk3NDc2MzY2ODQ2NzczZDNkIn0.S5POWfuY23y6isIPp3Hju9AWvYW4PGbAz8BtWLgGWhQ"
            //Logger.d("token: $token")
            val request = chain.request().newBuilder()
//            .header("Authorization", String.format("Bearer %s", token))
                .header("token", String.format("%s", token))
                .header("Application-Version", AppCommon.getVersionName(context))
                .build()
            val response = chain.proceed(request)

            // fix for response 200 vs re-format response
            try {
                if (response.code == 200) {
                    var jsonObject = JSONObject()
                    val bodyStr = response.body?.string()
                    if (bodyStr.isNullOrEmpty() || bodyStr == "{}") {
                        jsonObject.put("status", 200)
                        jsonObject.put("message", "SUCCESS")
                    } else {
                        jsonObject = JSONObject(bodyStr)
                    }

                    val contentType = response.body?.contentType()
                    val body = ResponseBody.create(contentType, jsonObject.toString())
                    //val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    return response.newBuilder().body(body).build()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return response
        }
    }

    @Provides
    @Singleton
    fun provideBaseApi(retrofit: Retrofit): BaseApi {
        return retrofit.create<BaseApi>(BaseApi::class.java)
    }
}