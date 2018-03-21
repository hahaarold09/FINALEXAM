package com.delacerna.finalexam

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.album_list_item.view.*

/**
 * Created by Harold on 3/21/2018.
 */
class AlbumAdapter(var context: Context,
                   private var searchAlbum: ArrayList<SearchAlbum>):RecyclerView.Adapter<CustomViewHolder>(){
    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {

        holder?.view?.txtAlbum?.text = searchAlbum[position].name
        holder?.view?.txtAlbum?.text = searchAlbum[position].artist
        val imgHolder = holder?.view?.imgView
        Picasso.with(holder?.view?.context).load(searchAlbum[position].imageURL.text).into(imgHolder)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.album_list_item, parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchAlbum.size
    }

}

class CustomViewHolder (var view: View): RecyclerView.ViewHolder(view){

}