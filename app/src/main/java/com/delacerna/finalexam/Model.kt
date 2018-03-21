package com.delacerna.finalexam

import com.google.gson.annotations.SerializedName

/**
 * Created by Harold on 3/21/2018.
 */
data class SearchAlbum( val name: String,
                        val artist: String,
                        @SerializedName("#text")
                        val text: String)