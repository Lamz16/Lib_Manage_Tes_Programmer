package com.lamz.libmanage.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lamz.libmanage.data.entity.BookEntity
import com.lamz.libmanage.data.entity.BorrowEntity
import com.lamz.libmanage.data.entity.HistoryEntity
import com.lamz.libmanage.data.entity.ReturnsEntity

@Database(entities = [BookEntity::class,ReturnsEntity::class,HistoryEntity::class, BorrowEntity::class], version = 1, exportSchema = false)
abstract class LibManDatabase : RoomDatabase() {
    abstract fun libManDao(): LibManDao
}

