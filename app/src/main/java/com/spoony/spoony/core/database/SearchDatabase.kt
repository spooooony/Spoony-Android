package com.spoony.spoony.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spoony.spoony.core.database.entity.SearchEntity

@Database(
    entities = [SearchEntity::class],
    version = 1
)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun SearchDao(): SearchDao
}
