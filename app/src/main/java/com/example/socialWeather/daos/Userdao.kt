package com.example.socialWeather.daos

import com.example.socialWeather.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Userdao {
    private val db =FirebaseFirestore.getInstance()
    private val userCollection =db.collection("user")
    fun addUser(user: User?){
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                userCollection.document(user.uid).set(it)
            }
        }
    }
    fun getUserById(id: String): Task<DocumentSnapshot> {
        return userCollection.document(id).get()
    }
}