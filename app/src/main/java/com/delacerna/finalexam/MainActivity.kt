package com.delacerna.finalexam

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {


    private val url = "https://ws.audioscrobbler.com/2.0/?method=album.search&album="
    private val url1 = "&api_key=07b17a11c897d35ca6252225d00b68be&format=json"
    private val addAlbum = ArrayList<SearchAlbum>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                fetchAlbum(query)
                return true
            }

        })
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun fetchAlbum(text: String) {
        for (i in 0..49) {
            doAsync {
                val tempText :String = text
                val resultJson = URL(url+tempText+url1).readText()
                val jsonObject = JSONObject(resultJson)

                val albumName = jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album")
                        .getJSONObject(i).getString("name")

                val artistName = jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album")
                        .getJSONObject(i).getString("artist")
                var imgName = ""

                imgName = if(jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album")
                        .getJSONObject(i).getJSONArray("image").getJSONObject(2).getString("#text") == ""){
                    jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album")
                            .getJSONObject(i).getJSONArray("image").getJSONObject(2).getString("")
                }else{
                    jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album")
                            .getJSONObject(i).getJSONArray("image").getJSONObject(2).getString("#text")
                }


                uiThread {
                    recyclerView.adapter = AlbumAdapter(this@MainActivity, addAlbum)
                    addAlbum.add(SearchAlbum(albumName,artistName, imgName))

                }
            }
        }
    }
}
