package com.lamz.libmanage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lamz.libmanage.data.LibManRepository
import com.lamz.libmanage.data.entity.BookEntity
import kotlinx.coroutines.launch

class MainViewModel(private val repository: LibManRepository) : ViewModel(){
    fun getSession() = repository.getSession().asLiveData()

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun insertBook(book: BookEntity) {
        viewModelScope.launch {
            repository.insertBook(book)
        }
    }
}