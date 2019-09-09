package io.vinter.wakemeup.ui.friends

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.vinter.wakemeup.R
import io.vinter.wakemeup.ui.view.BaseDialog
import kotlinx.android.synthetic.main.fragment_add_friend.*

class AddFriendFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friend_search_add.setOnClickListener {
            if (friend_query.text.toString() != "") {
                (targetFragment as FriendsFragment).addFriend(friend_query.text.toString())
                dialog?.dismiss()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        return BaseDialog.get(dialog, R.layout.fragment_add_friend)
    }

}
