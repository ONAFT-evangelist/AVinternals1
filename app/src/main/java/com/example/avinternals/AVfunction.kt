package com.example.avinternals

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File

val audioPath : String = Environment.getExternalStoragePublicDirectory(
    Environment.DIRECTORY_MUSIC).toString()+"/AVaudio/"
val videoPath : String = Environment.getExternalStoragePublicDirectory(
    Environment.DIRECTORY_MOVIES).toString()+"/AVvideo/"
val listOfAllVideos: MutableList<Uri> = mutableListOf()
val listOfAllAudio: MutableList<Uri> = mutableListOf()

fun getVideos (c : Context) : MutableList<Uri> {
    listOfAllVideos.clear()
    val uriExternal: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    val cursor: Cursor?
    val columnIndexID: Int
    val sortOrder = MediaStore.Video.Media.DATE_TAKEN
    val projection = arrayOf(MediaStore.Video.Media._ID)
    var videoId: Long
    cursor = c.contentResolver.query(uriExternal, projection, null, null, sortOrder)
    if (cursor != null) {
        println("CURSOR NOT NULL")
        columnIndexID = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        while (cursor.moveToNext()) {
            videoId = cursor.getLong(columnIndexID)
            val uriVideo = Uri.withAppendedPath(uriExternal, "" + videoId)
            listOfAllVideos.add(uriVideo)
        }
        cursor.close()
    } else {
        println("CURSOR NULL") }
    if (listOfAllVideos.size > 0) {listOfAllVideos.reverse()}
    return listOfAllVideos
}

fun getAudio (c : Context) : MutableList<Uri> {
    listOfAllAudio.clear()
    val uriExternal: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val cursor: Cursor?
    val columnIndexID: Int
    val projection = arrayOf(MediaStore.Audio.Media._ID)
    var audioId: Long
    val sortOrder = MediaStore.Audio.Media.DATE_ADDED
    cursor = c.contentResolver.query(uriExternal, projection, null, null, sortOrder)
    if (cursor != null) {
        columnIndexID = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        while (cursor.moveToNext()) {
            audioId = cursor.getLong(columnIndexID)
            val uriVideo = Uri.withAppendedPath(uriExternal, "" + audioId)
            listOfAllAudio.add(uriVideo)
        }
        cursor.close()
    }
    return listOfAllAudio
}

fun mkDir () {
    val audioDir = File(audioPath)
    val videoDir = File(videoPath)
    if ( !audioDir.exists() && !videoDir.exists()) {
        audioDir.mkdir()
        videoDir.mkdir()
    }
}