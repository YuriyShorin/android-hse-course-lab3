package hse.course.android_hse_course_lab3.api

import hse.course.android_hse_course_lab3.model.NewsData

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsDataApiService {

    @GET("news")
    fun getNewsData(
        @Query("apikey") apiKey: String,
        @Query("q") query: String,
        @Query("language") language: String
    ): Call<NewsData>

    companion object Factory {

        fun create(): NewsDataApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsdata.io/api/1/")
                .build()

            return retrofit.create(NewsDataApiService::class.java);
        }
    }
}