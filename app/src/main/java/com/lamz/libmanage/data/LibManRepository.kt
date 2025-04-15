package com.lamz.libmanage.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.lamz.libmanage.data.entity.BookEntity
import com.lamz.libmanage.data.entity.BorrowEntity
import com.lamz.libmanage.data.entity.HistoryEntity
import com.lamz.libmanage.data.entity.ReturnsEntity
import com.lamz.libmanage.data.pref.UserModel
import com.lamz.libmanage.data.pref.UserPreference
import com.lamz.libmanage.data.room.LibManDao
import kotlinx.coroutines.flow.Flow

class LibManRepository(
    private val libManDao: LibManDao,
    private val userPreference: UserPreference
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun insertBook(book: BookEntity) {
        libManDao.insertBook(book)
    }

    fun getBooks(): LiveData<List<BookEntity>> = libManDao.getAllBooks().asLiveData()

    suspend fun getBookById(bookId: Int): BookEntity? {
        return libManDao.getBookById(bookId)
    }

    suspend fun deleteBook(book: BookEntity) = libManDao.deleteBook(book)

    suspend fun updateBook(book: BookEntity) = libManDao.updateBook(book)

    suspend fun borrowBook(book: BookEntity, date: String) {
        val updatedBook = book.copy(stock = book.stock - 1)
        val borrow = BorrowEntity(bookId = book.id, borrDate = date)
        val history = HistoryEntity(bookId = book.id, date = date, status = "dipinjam")

        libManDao.insertBorrow(borrow)
        libManDao.insertHistory(history)
        libManDao.updateBook(updatedBook)
    }

    suspend fun returnBook(book: BookEntity, returnDate: String) {
        val updatedBook = book.copy(stock = book.stock + 1)
        val returnEntity = ReturnsEntity(bookId = book.id, returnDate = returnDate)
        val history = HistoryEntity(bookId = book.id, date = returnDate, status = "dikembalikan")

        libManDao.insertReturn(returnEntity)
        libManDao.insertHistory(history)
        libManDao.updateBook(updatedBook)
    }

    fun getHistory(): Flow<List<HistoryEntity>> = libManDao.getAllHistory()



}