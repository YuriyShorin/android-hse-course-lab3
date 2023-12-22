package hse.course.android_hse_course_lab3.model

import com.google.gson.annotations.SerializedName

data class NewsData (

    @SerializedName("results")
    val news: MutableList<News>?,
)
