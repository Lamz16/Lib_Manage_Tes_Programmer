package com.lamz.libmanage.presentation.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lamz.libmanage.data.LibManRepository
import com.lamz.libmanage.data.entity.BookEntity
import com.lamz.libmanage.data.entity.HistoryEntity

class HistoryViewModel(private val repository: LibManRepository) : ViewModel() {

    fun getHistory(): LiveData<List<HistoryEntity>> {
        return repository.getHistory().asLiveData()
    }

    suspend fun getBookById(bookId: Int): BookEntity? {
        return repository.getBookById(bookId)
    }

}