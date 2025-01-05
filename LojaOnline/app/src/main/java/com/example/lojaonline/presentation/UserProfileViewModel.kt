//package com.example.lojaonline.presentation
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewModelScope
//import com.example.lojaonline.data.local.SessionManager
//import com.example.lojaonline.domain.repository.UserRepository
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.launch
//
//class UserProfileViewModel(
//    private val sessionManager: SessionManager,
//    private val userRepository: UserRepository
//) : ViewModel() {
//
//    private val _userState = MutableStateFlow<UserProfileState>(UserProfileState.Loading)
//    val userState: StateFlow<UserProfileState> = _userState.asStateFlow()
//
//    init {
//        loadUserProfile()
//    }
//
//    private fun loadUserProfile() {
//        viewModelScope.launch {
//            try {
//                val token = sessionManager.userToken.first()
//                if (token != null) {
//                    val user = userRepository.getUserProfile(token)
//                    _userState.value = UserProfileState.Success(user.username, token)
//                } else {
//                    _userState.value = UserProfileState.Error("No token found")
//                }
//            } catch (e: Exception) {
//                _userState.value = UserProfileState.Error(e.message ?: "Unknown error")
//            }
//        }
//    }
//
//    fun logout() {
//        viewModelScope.launch {
//            sessionManager.clearUserToken()
//            _userState.value = UserProfileState.Loading
//        }
//    }
//
//    class Factory(
//        private val sessionManager: SessionManager,
//        private val userRepository: UserRepository
//    ) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return UserProfileViewModel(sessionManager, userRepository) as T
//            }
//            throw IllegalArgumentException("Unknown ViewModel class")
//        }
//    }
//}
//
//sealed class UserProfileState {
//    object Loading : UserProfileState()
//    data class Success(val username: String, val token: String) : UserProfileState()
//    data class Error(val message: String) : UserProfileState()
//}
//
