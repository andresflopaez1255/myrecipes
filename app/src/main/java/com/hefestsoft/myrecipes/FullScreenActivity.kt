package com.hefestsoft.myrecipes

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile

import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.hefestsoft.myrecipes.databinding.ActivityFullScreenBinding

class FullScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullScreenBinding
    var player: SimpleExoPlayer? = null
    private var playerWithReady = true
    private var currentWindows =0
    private var playBackPosition:Long = 0
    private var url:String= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
         url = intent.getStringExtra("url")!!
        val buttonFullScreen = view.findViewById<ImageView>(R.id.exo_fullscreen_icon)
        buttonFullScreen.setImageResource(R.drawable.exo_controls_fullscreen_exit)
        buttonFullScreen.setOnClickListener {
            super.onBackPressed();

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }


    @android.annotation.SuppressLint("StaticFieldLeak")
    private fun initPlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        binding.videoPlayerFull.player = player
        val youtubeLink = url.replace("[\\\\><\"|*?%:#/]", "");
        Log.i("youtube",youtubeLink)

        object: YouTubeExtractor(this){

            @android.annotation.SuppressLint("StaticFieldLeak")
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {
                if (ytFiles!=null){
                    val itag = 137 // tag for 1080p video
                    val audioTag = 140 // tag for audio video
                    val audioUrl = ytFiles[audioTag].url
                    val videoUr = ytFiles[itag].url


                    if(videoUr !=null && audioUrl !=null ){
                        val  audioSource: MediaSource = ProgressiveMediaSource
                            .Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(audioUrl))


                        val  videoSource: MediaSource = ProgressiveMediaSource
                            .Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(videoUr))

                        player!!.setMediaSource(MergingMediaSource(true,videoSource,audioSource),true)
                        player!!.prepare()
                        player!!.playWhenReady = playerWithReady
                        player!!.seekTo(currentWindows,playBackPosition)
                    }
                }
            }

        }.extract(youtubeLink,false,true)



    }


    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >=24){
            Thread{

                initPlayer()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <24 || player==null){
            initPlayer()
            hideSystemUI()
        }
    }
    @SuppressLint("InlinedApi")
    private fun hideSystemUI() {

        binding.videoPlayerFull.systemUiVisibility =(View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT<24) releasePlayer()
    }

    private fun releasePlayer() {
        if (player!= null){
            playerWithReady = player!!.playWhenReady
            playBackPosition = player!!.currentPosition
            currentWindows = player!!.currentWindowIndex
            player!!.release()
            player = null

        }
    }

    // end methods for player video

}