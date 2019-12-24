package io.vinter.wakemeup.utils

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.shehabic.droppy.DroppyMenuPopup
import com.shehabic.droppy.animations.DroppyFadeInAnimation
import io.vinter.wakemeup.R
import io.vinter.wakemeup.entity.friends.Friend
import kotlinx.android.synthetic.main.item_friend.view.*

class PairRecyclerAdapter(
        private val friends: ArrayList<Friend>,
        private val listener: (String) -> Unit,
        private val longTapListener: (Friend) -> Unit
): RecyclerView.Adapter<PairRecyclerAdapter.PairViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PairViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val itemView = inflater.inflate(R.layout.item_friend, p0, false)
        return PairViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    override fun onBindViewHolder(holder: PairViewHolder, position: Int) {
        holder.bind(friends[position], listener, longTapListener)
    }

    class PairViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(
                item: Friend,
                listener: (String) -> Unit,
                longTapListener: (Friend) -> Unit
        ) = with (itemView) {
            pair_name.text = item.login
            pair_wakeup.setOnClickListener { item.id?.let(listener) }
            GlideApp.with(context)
                    .load(item.pictureURL)
                    .override(200, 200)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .transform(CircleCrop())
                    .into(pair_pic)

            val dropDownBuilder = DroppyMenuPopup.Builder(context, pair_pic)
            val dropDown = dropDownBuilder.fromMenu(R.menu.friend_dropdown)
                    .triggerOnAnchorClick(false)
                    .setOnClick { _, _ -> longTapListener(item) }
                    .setPopupAnimation(DroppyFadeInAnimation())
                    .build()

            itemView.setOnLongClickListener {
                dropDown.show()
                true
            }
        }
    }

    fun remove(friend: Friend) {
        val i = friends.indexOf(friend)
        friends.remove(friend)
        notifyItemRemoved(i)
        notifyItemRangeChanged(i, friends.size)
    }
}
