package com.example.avinternals

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AudioAdapter (val audioList : MutableList<Uri>, val context: MainActivity) : RecyclerView.Adapter<AudioAdapter.AudioHolder>() {
    lateinit var mClickListener: OnItemClickListener
    private lateinit var mediaPlayer : MediaPlayer
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        return AudioHolder(LayoutInflater.from(context).inflate(R.layout.audio_item, parent, false))
    }

    override fun getItemCount(): Int {
        return audioList.size
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        holder.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(v: View?, position: Int) {
                try {
                    stopPlaying()
                    mediaPlayer = MediaPlayer.create(context , audioList.get(position))
                    mediaPlayer.start()
                } catch (ex : NullPointerException){
                    Toast.makeText(context , "CLICK"+audioList.get(position) , Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun stopPlaying() {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
        }
    }

    inner class AudioHolder(v: View) : RecyclerView.ViewHolder(v),  View.OnClickListener {

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