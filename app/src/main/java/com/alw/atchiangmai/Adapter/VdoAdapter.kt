package com.alw.atchiangmai.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.ModelYoutube
import com.alw.atchiangmai.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.vdo_card.view.*
class VdoAdapter(private val arrayList: ArrayList<ModelYoutube>): RecyclerView.Adapter<VdoAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        fun bindVdo(modelYoutube: ModelYoutube){

            itemView.videoView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = modelYoutube.id
                    youTubePlayer.loadVideo(videoId, 0F)
                    youTubePlayer.mute()
                }
            })
            itemView.textVdo.text = modelYoutube.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vdo_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindVdo(arrayList[position])

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}