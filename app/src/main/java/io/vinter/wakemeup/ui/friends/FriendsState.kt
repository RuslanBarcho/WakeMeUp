package io.vinter.wakemeup.ui.friends

import io.vinter.wakemeup.entity.Friend

sealed class FriendsState {
    class Initial : FriendsState()
    class Loading : FriendsState()
    class Success(val friends: ArrayList<Friend>): FriendsState()
    class Error: FriendsState()
}