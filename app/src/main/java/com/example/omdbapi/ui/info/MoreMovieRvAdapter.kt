package com.example.omdbapi.ui.info

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbapi.R
import com.example.omdbapi.retrofit.Search
import com.example.omdbapi.ui.main.MoviesRvAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_rv_movie_item.view.*

class MoreMovieRvAdapter(val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list: MutableList<Search> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder  =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_rv_more_movie_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val movie = list[position]

        viewHolder.tv_title.text = movie.title
        Picasso.get().load(movie.poster).into(viewHolder.imv_poster)

        viewHolder.itemView.setOnClickListener {
            var intent = Intent(context, MovieInfoActivity::class.java)
            intent.putExtra("ID",movie.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = list.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_title = itemView.tv_title
        var imv_poster = itemView.imv_poster

    }

    fun replace(list: List<Search>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun addAll(list: List<Search>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun addItem(movie: Search) {
        list.add(movie)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyDataSetChanged()
    }

}