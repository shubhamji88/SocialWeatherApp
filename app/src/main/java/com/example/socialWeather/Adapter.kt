package com.example.socialWeather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialWeather.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class PostAdapter(options: FirestoreRecyclerOptions<Post>,val listner: IPostAdapter) : FirestoreRecyclerAdapter<Post,PostAdapter.PostViewHolder>(options) {
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postText: TextView = itemView.findViewById(R.id.postTitle)
        val postText2: TextView = itemView.findViewById(R.id.post2nd)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val postViewHolder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))
        postViewHolder.likeButton.setOnClickListener {
            listner.onLikeClicked(snapshots.getSnapshot(postViewHolder.adapterPosition).id)
        }
        return postViewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.postText.text = "Current Temp: "+model.temp+"°C"
        holder.postText2.text="Min Temp: "+model.mintem+"°C "+"Max Temp: "+model.maxtem+"°C "
        holder.userText.text = model.createdby.displatName
        Glide.with(holder.userImage.context).load(model.createdby.imgurl).circleCrop().into(holder.userImage)
            holder.likeCount.text=model.likedBy.size.toString()
        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)
        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        val isLiked = model.likedBy.contains(currentUserId)
        if(isLiked) {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_like))
        } else {
            holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.context, R.drawable.ic_unlike))
        }

    }

    interface IPostAdapter {
        fun onLikeClicked(postId: String)
    }
}