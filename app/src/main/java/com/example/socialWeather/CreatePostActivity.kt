package com.example.socialWeather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.socialWeather.api.WeatherApi
import com.example.socialWeather.api.response.WeatherResponse
import com.example.socialWeather.daos.PostDao
import com.example.socialWeather.databinding.ActivityCreatePostBinding
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class CreatePostActivity : AppCompatActivity() {
    private lateinit var postDao: PostDao
    private lateinit var weather:WeatherResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        val binding=DataBindingUtil.setContentView<ActivityCreatePostBinding>(this,R.layout.activity_create_post)
        postDao= PostDao()

        GlobalScope.launch {
            weather=WeatherApi.retrifitService.getWeather().await()
            withContext(Dispatchers.Main){
                binding.address.text=weather.name.toString()
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(weather.dt.toLong()*1000))
                binding.updatedAt.text=updatedAtText
                binding.temp.text=weather.main.temp.toString()+"°C"
                binding.humidity.text=weather.main.humidity.toString()
                binding.tempMax.text="Min Temp: "+weather.main.temp_max.toString()+"°C"
                binding.tempMin.text="Max Temp: "+weather.main.temp_min.toString()+"°C"
                binding.wind.text=weather.wind.speed.toString()+"Km/hr"
                binding.pressure.text=weather.main.pressure.toString()
            }
        }
        binding.post.setOnClickListener {
            postDao.addPost(weather)
            finish()
        }


//        post.setOnClickListener {
//            val input=textView.text.toString().trim()
//            if(input.isNotEmpty()){
//                postDao.addPost(input)
//                finish()
//            }
//        }
    }
    suspend fun getWeather(){

    }


}