import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.data.remote.api.RetrofitInstance
import com.example.lojaonline.data.repository.UserRepositoryImpl
import com.example.lojaonline.domain.model.UserAdd
import com.example.lojaonline.domain.use_case.AddUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterUserViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = UserRepositoryImpl(api, sessionManager)
    private val addUserUseCase = AddUserUseCase(repository)

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    fun registerUser(userAdd: UserAdd) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            try {
                addUserUseCase(userAdd)
                _registerState.value = RegisterState.Success
                Log.d("RegisterViewModel", "Registering user: $userAdd")
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error(e.message ?: "Unknown error")
                Log.e("RegisterViewModel", "Error registering user: ${e.message}")
            }
        }
    }

    class Factory(private val sessionManager: SessionManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterUserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegisterUserViewModel(sessionManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}

