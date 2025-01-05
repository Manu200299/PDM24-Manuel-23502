package com.example.lojaonline.presentation
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.data.remote.api.RetrofitInstance
import com.example.lojaonline.data.repository.UserRepositoryImpl
import com.example.lojaonline.domain.model.User
import com.example.lojaonline.domain.use_case.GetUserByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class UserProfileViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = UserRepositoryImpl(api, sessionManager)
    private val getUserProfileUseCase = GetUserByIdUseCase(repository)

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            try {
                val userId = sessionManager.userID.first()
                if (userId != null) {
                    val user = getUserProfileUseCase(userId)
                    _profileState.value = ProfileState.Success(user)
                    Log.d("UserProfileViewModel", "Loaded user profile: $user")
                } else {
                    _profileState.value = ProfileState.Error("No user ID found")
                    Log.e("UserProfileViewModel", "No user ID found")
                }
            } catch (e: Exception) {
                _profileState.value = ProfileState.Error(e.message ?: "Unknown error")
                Log.e("UserProfileViewModel", "Error loading user profile: ${e.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                sessionManager.clearSession()
                _profileState.value = ProfileState.Idle
                Log.d("UserProfileViewModel", "User logged out")
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Error during logout: ${e.message}")
            }
        }
    }

    class Factory(
        private val sessionManager: SessionManager
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserProfileViewModel(sessionManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

sealed class ProfileState {
    object Idle : ProfileState()
    object Loading : ProfileState()
    data class Success(val user: User) : ProfileState()
    data class Error(val message: String) : ProfileState()
}

