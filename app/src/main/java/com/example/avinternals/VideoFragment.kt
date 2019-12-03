
package com.example.avinternals

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetFileDescriptor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_video.*
import java.io.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

const val REQUEST_VIDEO_CAPTURE = 101
private lateinit var gridLayoutManager: GridLayoutManager
private lateinit var adapter: VideoAdapter

class VideoFragment : Fragment() {
    var cVideoPath: String = ""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_video, container, false)
        val shutter = view.findViewById<FloatingActionButton>(R.id.shutter)
        val videoRecycler = view.findViewById<RecyclerView>(R.id.videoRecycler)
        activity as MainActivity
        shutter.setOnClickListener(clickListen)
        gridLayoutManager = GridLayoutManager(activity , 4)
        videoRecycler.layoutManager = gridLayoutManager
        adapter = VideoAdapter(listOfAllVideos , activity as MainActivity)
        videoRecycler.adapter = adapter

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val clickListen = View.OnClickListener {
        val packageManager : PackageManager = activity!!.packageManager
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                val videoFile : File? = try {
                    createFile()
                } catch (ex: IOException){
                    Toast.makeText(activity , "file creation error: "+ex, Toast.LENGTH_LONG).show()
                    null
                }
                videoFile?.also {
                    val videoUri: Uri = FileProvider.getUriForFile(
                        this.context!!,
                        "com.example.avinternals.fileprovider",
                        it
                    )
                    takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri)
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
                }
            }
        }
    }

    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_VIDEO_CAPTURE->{
                if(resultCode == Activity.RESULT_OK && data != null){
                    val dataUri : Uri = data.data
                    Toast.makeText(activity , "saved to: $dataUri", Toast.LENGTH_LONG).show()
                } else if (requestCode == Activity.RESULT_CANCELED){
                    Toast.makeText(activity , "recording cancelled!" , Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(activity , "recording failed!" , Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun createFile () : File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val vPath : File = File(videoPath)
        return File.createTempFile(
            "video_${timeStamp}_",
        ".mp4",
                vPath
        ).apply{
            cVideoPath = absolutePath
        }
    }

}

