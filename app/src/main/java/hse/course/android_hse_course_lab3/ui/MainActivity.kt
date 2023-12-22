package hse.course.android_hse_course_lab3.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import hse.course.android_hse_course_lab3.R
import hse.course.android_hse_course_lab3.adapter.NewsAdapter
import hse.course.android_hse_course_lab3.utils.Languages
import hse.course.android_hse_course_lab3.model.NewsData
import hse.course.android_hse_course_lab3.api.NewsDataApiService
import hse.course.android_hse_course_lab3.databinding.MainActivityLayoutBinding
import hse.course.android_hse_course_lab3.ui.settings.SettingsActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity: AppCompatActivity() {

    private lateinit var binding: MainActivityLayoutBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityLayoutBinding.inflate(layoutInflater)
        init()
    }

    private fun init() {
        supportActionBar?.hide()
        setContentView(binding.root)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.newsRecyclerView.layoutManager = linearLayoutManager
        binding.newsRecyclerView.adapter =NewsAdapter(ArrayList())

        sharedPreferences =
            applicationContext.getSharedPreferences(
                getString(R.string.storage_key),
                Context.MODE_PRIVATE
            )

        val favoriteTopic: String? =
            sharedPreferences.getString(getString(R.string.favorite_topic_key), null)

        if (favoriteTopic != null) {
            binding.searchEditText.setText(favoriteTopic)
            performSearch()
        }

        binding.settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }


        binding.searchButton.setOnClickListener {
            performSearch()
        }
    }

    private fun performSearch() {
        val query: String = binding.searchEditText.text.toString()

        if (query.isEmpty()) {
            Log.v("INFO", "Empty search was performed")
            return
        }

        Log.v("INFO", "Search is performing: $query")

        val language: String? = sharedPreferences.getString(getString(R.string.language_key), null)
        val languageAbbreviation: String = if (language == null) {
            Languages.getLanguageAbbreviation(Languages.English.name)
        } else {
            Languages.getLanguageAbbreviation(language)
        }

        val newsDataApiService: NewsDataApiService = NewsDataApiService.create()
        newsDataApiService
            .getNewsData(
                "pub_34302aaf1c1101da62f408fed4dc9a581b91b", query, languageAbbreviation
            )
            .enqueue(object : Callback<NewsData> {

                override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                    val newsData: NewsData? = response.body()
                    Log.v(
                        "INFO",
                        "Response has been received\nurl: " + call.request()
                            .url() + "\ntotal results: " + newsData?.news?.size
                    )

                    if (newsData?.news?.size == 0) {
                        binding.resultsFoundTextView.text =
                            getString(R.string.results_found, newsData.news.size)
                        binding.resultsFoundTextView.visibility = View.VISIBLE
                    } else {
                        binding.resultsFoundTextView.visibility = View.INVISIBLE
                    }

                    binding.newsRecyclerView.adapter = newsData?.news?.let { NewsAdapter(it) }
                }

                override fun onFailure(call: Call<NewsData>, t: Throwable) {
                    Log.v(
                        "INFO",
                        "Call" + call.request().url() + "has been failed\n Reason: " + t.message
                    )
                }
            })
    }
}