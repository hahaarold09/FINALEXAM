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

    private val addAlbum = ArrayList<SearchAlbum>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchAlbum()

        recyclerView.layoutManager = LinearLayoutManager(this)

        editTextView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                fetchAlbum()
                return false
            }

        })
    }

    private fun fetchAlbum() {
        for (i in 1..50) {
        doAsync {
            var tempAlbum = editTextView.toString()
            val resultJson = URL("http://ws.audioscrobbler.com/2.0/?method=album.search&album="+tempAlbum+"&api_key=07b17a11c897d35ca6252225d00b68be").readText()
            val jsonObject = JSONObject(resultJson)
            val albumName = jsonObject.getJSONObject("albummatches").getJSONArray("album")
                    .getJSONObject(0).getString("name")
            val artistName = jsonObject.getJSONObject("albummatches").getJSONArray("album")
                    .getJSONObject(0).getString("artist")
            val imgUrl =  jsonObject.getJSONObject("albummatches").getJSONArray("album").getJSONObject(0)
                    .getJSONArray("image").getJSONObject(0).getString("#text")
            uiThread {
                recyclerView.adapter = AlbumAdapter( this@MainActivity,addAlbum)
               addAlbum.add(SearchAlbum(albumName,artistName,Image(imgUrl)))

            }
        }
        }
    }
}
