package com.spoony.spoony.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "search"
)
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String
)
