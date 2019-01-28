package svyatoslavbabyak.com.spacex.rest

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import svyatoslavbabyak.com.spacex.BuildConfig
import svyatoslavbabyak.com.spacex.entity.CompanyInfo
import svyatoslavbabyak.com.spacex.entity.Launch
import java.util.*

interface SpaceXClient {
    @GET("/v3/launches")
    fun getLaunches(@Query("launch_year") launchYear: Int?) : Observable<ArrayList<Launch>>

    @GET("/v3/info")
    fun getCompanyInfo() : Observable<CompanyInfo>

    companion object Factory {
        fun create(): SpaceXClient {
            val okHttpClient = OkHttpClient()

            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create()

            val baseUrl = BuildConfig.BASE_URL

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()

            return retrofit.create(SpaceXClient::class.java)
        }
    }
}