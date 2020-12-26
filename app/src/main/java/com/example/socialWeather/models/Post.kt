package com.example.socialWeather.models

data class Post (
    val text:String="",
    val createdby:User=User(),
    val createdAt: Long=0L,

    val temp:String="",
    val maxtem:String="",
    val mintem:String="",
    val loc:String="",
    val likedBy: ArrayList<String> = ArrayList())

