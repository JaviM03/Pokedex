package com.example.ejemplo07.Utilities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.example.ejemplo07.R
import org.json.JSONObject
import java.io.IOException

class activityFile : AppCompatActivity() {

    private lateinit var mETNombre: TextView
    private lateinit var mETWeight: TextView
    private lateinit var mETAltura: TextView
    private lateinit var mETExp: TextView
    private lateinit var mperfilmagenPokemon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)
        mETNombre= findViewById(R.id.profile_name_tv)
        mETWeight= findViewById(R.id.profile_weight_tv)
        mETAltura= findViewById(R.id.profile_height_tv)
        mETExp= findViewById(R.id.profile_experience_tv)


        var intent: Intent = getIntent()
        FetchDataPokemon().execute()
        //Log.v("xd",intent.getStringExtra("URLPokemon"))
    }

    private inner class FetchDataPokemon: AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg params: String?): String {
            val pokeAPI = NetworkUtils.buildUrl("pokemon", intent.getStringExtra("IDPokemon"))
            return try {
                NetworkUtils.getResponseFromHttpUrl(pokeAPI)!!
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }
        }

        override fun onPostExecute(pokemonInfo: String?) {

            if (pokemonInfo != null || pokemonInfo != "") {

                // Glide.with(this@ActivityPokemonFicha).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+intent.getStringExtra("IDPokemon")+".png").into(mperfilmagenPokemon)

                var jsonPokeInfo= JSONObject(pokemonInfo)
                mETNombre.text=jsonPokeInfo.getString("name")
                mETWeight.text =jsonPokeInfo.getString("weight")
                mETAltura.text=jsonPokeInfo.getString("height")
                mETExp.text=jsonPokeInfo.getString("base_experience")


            }
        }
    }

}