package com.delacerna.finalexam

/**
 * Created by Harold on 3/21/2018.
 */
data class SearchAlbum( val name: String,
                        val artist: String,
                        val imageURL: Image)
data class Image(val text: String)