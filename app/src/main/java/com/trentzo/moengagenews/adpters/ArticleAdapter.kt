package com.trentzo.moengagenews.adpters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.trentzo.moengagenews.R
import com.trentzo.moengagenews.beans.OfflineArticleData
import com.trentzo.moengagenews.callBacks.OnArticleClickListener
import com.trentzo.moengagenews.utils.ConstantUtils
import kotlinx.android.synthetic.main.item_article_item.view.*


class ArticleAdapter(
    context: Context,
    articles: ArrayList<OfflineArticleData>,
    onArticleClickListener: OnArticleClickListener
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private var mContext: Context? = null
    private var articles: ArrayList<OfflineArticleData>? = null
    private var onArticleClickListener: OnArticleClickListener? = null

    init {
        mContext = context
        this.articles = articles
        this.onArticleClickListener = onArticleClickListener
    }

    fun updateAdapter(articles: ArrayList<OfflineArticleData>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_article_item, parent, false)
        return ViewHolder(view, mContext, onArticleClickListener)
    }

    override fun getItemCount(): Int {
        return articles!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(articles?.get(position), position)
    }

    class ViewHolder(
        itemView: View,
        private val mContext: Context?,
        val onArticleClickListener: OnArticleClickListener?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(offlineArticleData: OfflineArticleData?, position: Int) {
            itemView.image.setImageDrawable(null)
            if (offlineArticleData?.author.isNullOrEmpty()) {
                itemView.author.visibility = View.GONE
            } else
                itemView.author.text = offlineArticleData?.author
            if (offlineArticleData?.content.isNullOrEmpty()) {
                itemView.content.visibility = View.GONE
            } else
                itemView.content.text = offlineArticleData?.content

            if (offlineArticleData?.description.isNullOrEmpty()) {
                itemView.description.visibility = View.GONE
            } else
                itemView.description.text = offlineArticleData?.description

            if (offlineArticleData?.sourceName.isNullOrEmpty()) {
                itemView.source.visibility = View.GONE
            } else
                itemView.source.text = offlineArticleData?.sourceName

            if (offlineArticleData?.publishedAt.isNullOrEmpty()) {
                itemView.publishedAt.visibility = View.GONE
            } else
                itemView.publishedAt.text =
                    ConstantUtils.getDateTimeToShow(offlineArticleData?.publishedAt, false)
            Picasso.with(mContext).load(offlineArticleData?.urlToImage)
                .placeholder(R.drawable.reload)
                .into(itemView.image, object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError() {
                        itemView.image.visibility = View.GONE

                    }
                })

            itemView.parentLl.setOnClickListener {
                onArticleClickListener?.onArticleClick(offlineArticleData)
            }
        }
    }

}