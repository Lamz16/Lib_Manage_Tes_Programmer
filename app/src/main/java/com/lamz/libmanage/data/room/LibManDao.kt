package com.lamz.libmanage.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lamz.libmanage.data.entity.BookEntity
import com.lamz.libmanage.data.entity.BorrowEntity
import com.lamz.libmanage.data.entity.HistoryEntity
import com.lamz.libmanage.data.entity.ReturnsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LibManDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :bookId LIMIT 1")
    suspend fun getBookById(bookId: Int): BookEntity?

    @Delete
    suspend fun deleteBook(book: BookEntity)

    @Update
    suspend fun updateBook(book: BookEntity)

    @Insert
    suspend fun insertBorrow(borrow: BorrowEntity)

    @Insert
    suspend fun insertReturn(returns: ReturnsEntity)

    @Insert
    suspend fun insertHistory(history: HistoryEntity)

    @Query("SELECT * FROM history")
    fun getAllHistory(): Flow<List<HistoryEntity>>



}