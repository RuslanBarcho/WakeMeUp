package io.vinter.wakemeup.utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import io.vinter.wakemeup.R
import io.vinter.wakemeup.entity.Friend

class PairRecyclerAdapter(val context: Context, private var friends: ArrayList<Friend>, private var listener: (String) -> Unit)
    : RecyclerView.Adapter<PairRecyclerAdapter.PairViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PairViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val itemView = inflater.inflate(R.layout.item_friend, p0, false)
        return PairRecyclerAdapter.PairViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    override fun onBindViewHolder(holder: PairViewHolder, position: Int) {
        holder.name.text = friends[position].login
        holder.wakeUpButton.setOnClickListener {listener(friends[position].id!!)}
        GlideApp.with(context)
                .load(friends[position].pictureURL)
                .override(200, 200)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .transform(CircleCrop())
                .into(holder.picture)
    }

    class PairViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var wakeUpButton: ImageButton = itemView.findViewById(R.id.pair_wakeup)
        var picture: ImageView = itemView.findViewById(R.id.pair_pic)
        var name: TextView = itemView.findViewById(R.id.pair_name)
    }
}