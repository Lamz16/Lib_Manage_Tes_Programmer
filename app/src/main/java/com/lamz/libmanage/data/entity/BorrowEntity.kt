package com.lamz.libmanage.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey

import androidx.room.*

@Entity(
    tableName = "borrow",
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["bookId"])
    ]
)
data class BorrowEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bookId: Int,
    val borrDate: String,
)


