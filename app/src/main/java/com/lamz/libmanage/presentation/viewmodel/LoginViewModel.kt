package com.lamz.libmanage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamz.libmanage.data.LibManRepository
import com.lamz.libmanage.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LibManRepository): ViewModel() {


    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}