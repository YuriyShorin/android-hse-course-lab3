package hse.course.android_hse_course_lab3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import hse.course.android_hse_course_lab3.R
import hse.course.android_hse_course_lab3.model.News

class NewsAdapter(private val news: MutableList<News>) :

    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val content: TextView
        val link: TextView

        init {
            title = view.findViewById(R.id.header)
            content = view.findViewById(R.id.content)
            link = view.findViewById(R.id.link)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.news_recyclerview_row_item, viewGroup, false)

        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(newsViewHolder: NewsViewHolder, position: Int) {
        newsViewHolder.title.text = news[position].title
        newsViewHolder.content.text = news[position].content
        newsViewHolder.link.text = newsViewHolder.link.context.getString(R.string.link_to_source, news[position].link)
    }

    override fun getItemCount() = news.size

}
