package com.example.socialWeather.daos

import com.example.socialWeather.api.response.WeatherResponse
import com.example.socialWeather.models.Post
import com.example.socialWeather.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val db= FirebaseFirestore.getInstance()
    val postCollection=db.collection("posts")
    val auth=Firebase.auth
    fun addPost(resp: WeatherResponse) {
        val currentUserId = auth.currentUser!!.uid
        GlobalScope.launch {
            val userdao=Userdao()
            val user =userdao.getUserById(currentUserId).await().toObject(User::class.java)!!
            val currentTime= System.currentTimeMillis()
            val post= Post("",user,currentTime,resp.main.temp.toString(),resp.main.temp_max.toString(),resp.main.temp_min.toString(),resp.name)
            postCollection.document().set(post)
        }


    }
    fun getPostById(postId: String): Task<DocumentSnapshot> {
        return postCollection.document(postId).get()
    }

    fun updateLikes(postId: String) {
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)!!
            val isLiked = post.likedBy.contains(currentUserId)

            if(isLiked) {
                post.likedBy.remove(currentUserId)
            } else {
                post.likedBy.add(currentUserId)
            }
            postCollection.document(postId).set(post)
        }

    }
}