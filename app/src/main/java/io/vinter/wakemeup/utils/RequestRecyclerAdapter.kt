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
import io.vinter.wakemeup.entity.Request

class RequestRecyclerAdapter(val context: Context, private var requests: ArrayList<Request>, private var listener: (Int, Int) -> Unit)
    : RecyclerView.Adapter<RequestRecyclerAdapter.RequestViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RequestViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val itemView = inflater.inflate(R.layout.item_request, p0, false)
        return RequestRecyclerAdapter.RequestViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return requests.size
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.name.text = requests[position].from!!.login
        holder.acceptButton.setOnClickListener {listener(position, 0)}
        holder.cancelButton.setOnClickListener {listener(position, 1)}
        GlideApp.with(context)
                .load(requests[position].from!!.pictureURL)
                .override(200, 200)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .transform(CircleCrop())
                .into(holder.picture)
    }

    class RequestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var acceptButton: ImageButton = itemView.findViewById(R.id.request_accept)
        var cancelButton: ImageButton = itemView.findViewById(R.id.request_cancel)
        var picture: ImageView = itemView.findViewById(R.id.request_pic)
        var name: TextView = itemView.findViewById(R.id.request_name)
    }

    fun refresh(requests: ArrayList<Request>){
        this.requests = requests
        notifyDataSetChanged()
    }
}