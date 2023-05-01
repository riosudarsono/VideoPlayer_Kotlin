package com.rio.videoplayer.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rio.videoplayer.R
import com.rio.videoplayer.data.response.ResultResponse
import kotlinx.android.synthetic.main.view_video.view.*

class VideoAdapter(
    private val listener: Listener,
    private var data: MutableList<ResultResponse>
) : RecyclerView.Adapter<VideoAdapter.Holder>() {

    private var idData: Int = 0

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(data: MutableList<ResultResponse>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_video, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(mData: ResultResponse, position: Int) {
            with(itemView) {
                if (idData == mData.trackId) ly_view.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_transparent))
                else ly_view.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))

                tv_title.text = mData.trackCensoredName
                tv_kind.text = mData.kind

                ly_view.setOnClickListener {
                    idData = mData.trackId ?: 0
                    notifyDataSetChanged()
                    listener.onClick(mData)
                }
            }
        }

    }

    interface Listener {
        fun onClick(data: ResultResponse)
    }
}