package com.example.avinternals

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_audio.*

private lateinit var audioLayoutManager: GridLayoutManager
private lateinit var adapter: AudioAdapter
class AudioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_audio, container, false)
        val recorder = view.findViewById<FloatingActionButton>(R.id.recorder)
        recorder.setOnClickListener(clickListener)
        val audioRecycler = view.findViewById<RecyclerView>(R.id.audioRecycler)
        activity as MainActivity
        audioLayoutManager = GridLayoutManager(activity , 4)
        audioRecycler.layoutManager = audioLayoutManager
        adapter = AudioAdapter(listOfAllVideos , activity as MainActivity)
        audioRecycler.adapter = adapter
        return view
    }

    private val clickListener = View.OnClickListener {
        activity?.let{
            val intent = Intent (it, RecorderActivity::class.java)
            it.startActivity(intent)
        }
    }

}
