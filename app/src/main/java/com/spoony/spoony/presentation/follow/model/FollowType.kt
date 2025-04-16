package com.spoony.spoony.presentation.follow.model

enum class FollowType {
    FOLLOWER,
    FOLLOWING;
    
    fun toPageIndex(): Int = ordinal

    companion object {
        fun fromPageIndex(index: Int): FollowType = entries[index.mod(entries.size)]
    }

}
