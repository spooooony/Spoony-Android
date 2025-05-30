package com.spoony.spoony.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spoony.spoony.core.database.entity.ExploreRecentSearchEntity

@Database(
    entities = [ExploreRecentSearchEntity::class],
    version = 2
)
abstract class ExploreRecentSearchDatabase : RoomDatabase() {
    abstract fun ExploreRecentSearchDao(): ExploreRecentSearchDao
}
