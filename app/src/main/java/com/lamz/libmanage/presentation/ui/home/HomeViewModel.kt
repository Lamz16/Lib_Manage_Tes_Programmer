package com.lamz.libmanage.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lamz.libmanage.data.LibManRepository
import com.lamz.libmanage.data.entity.BookEntity
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: LibManRepository) : ViewModel() {
    fun getSession() = repository.getSession().asLiveData()
    fun getBooks(): LiveData<List<BookEntity>> = repository.getBooks()

    fun deleteBook(book: BookEntity) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    fun updateBook(book: BookEntity) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun borrowBook(book: BookEntity, date: String) {
        viewModelScope.launch {
            repository.borrowBook(book, date)
        }
    }

    fun returnBook(book: BookEntity, returnDate: String) {
        viewModelScope.launch {
            repository.returnBook(book, returnDate)
        }
    }


}