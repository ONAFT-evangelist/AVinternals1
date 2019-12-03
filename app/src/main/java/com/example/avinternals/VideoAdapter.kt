package com.example.avinternals

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.VideoView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.video_item.view.*

class VideoAdapter (
    val mediaWTList: MutableList<Uri>,
    val context: MainActivity) : RecyclerView.Adapter<VideoAdapter.VideoHolder>() {
    val videoView = context.findViewById<VideoView>(R.id.videoView)
    lateinit var mClickListener: OnItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false)
        return VideoHolder(view)
    }

    override fun getItemCount(): Int {
        return mediaWTList.size
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        holder.v_thumb.setImageBitmap(bitmapFromUri(position))
        holder.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(v: View?, position: Int) {
                try {
                    videoView.setVideoURI(mediaWTList.get(position))
                    videoView.requestFocus()
                    videoView.start()
                } catch (ex : NullPointerException){
                    Toast.makeText(context , "CLICK"+mediaWTList.get(position) , Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun bitmapFromUri(position: Int): Bitmap? {
        val thumb: Bitmap
        val mmd = MediaMetadataRetriever()
        val uri = mediaWTList.get(position)
        mmd.setDataSource(context, uri)
        thumb = mmd.frameAtTime
        return thumb
    }

  inner  class VideoHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val player = v.findViewById<VideoView>(R.id.videoView)
        val v_thumb = v.v_thumb
        val card = v.findViewById<CardView>(R.id.v_card)
      fun setOnItemClickListener (clickListener: OnItemClickListener) {
          mClickListener = clickListener
      }
      override fun onClick(p0: View) {
            mClickListener.onItemClick(p0 , adapterPosition)
        }

      init {
          card.setOnClickListener(this)
      }
    }
}