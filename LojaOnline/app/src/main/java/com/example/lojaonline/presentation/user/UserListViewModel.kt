package com.example.lojaonline.presentation.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.data.remote.api.RetrofitInstance
import com.example.lojaonline.data.repository.UserRepositoryImpl
import com.example.lojaonline.domain.model.User
import com.example.lojaonline.domain.use_case.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserListViewModel(
    private val sessionManager: SessionManager
): ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = UserRepositoryImpl(api, sessionManager)
    private val getUsersUseCase = GetUsersUseCase(repository)

    val users = MutableStateFlow<List<User>>(emptyList())

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = getUsersUseCase()
                Log.d("ViewModel", "Data for UI: $response")
                users.value = response
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching users: ${e.message}")
                users.value = emptyList()
            }
        }
    }
}