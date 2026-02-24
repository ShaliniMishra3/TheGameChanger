package com.example.thegamechanger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thegamechanger.UiState
import com.example.thegamechanger.model.InnerLoginWrapper
import com.example.thegamechanger.model.LoginRequest
import com.example.thegamechanger.model.LoginResponse
import com.example.thegamechanger.repository.DealerRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: DealerRepository
) : ViewModel() {
    val _navigateToMain = MutableStateFlow<Pair<Int, String>?>(null)
    val navigateToMain: StateFlow<Pair<Int, String>?> = _navigateToMain


    private val _loginState =
        MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val loginState = _loginState.asStateFlow()

    private val _navigateToManager = MutableStateFlow<Boolean?>(null)
    val navigateToManager = _navigateToManager.asStateFlow()


  /* fun login(email: String, password: String, role: String) {
       viewModelScope.launch {

           _loginState.value = UiState.Loading

           val result = if (role == "Manager") {
               repository.managerLogin(LoginRequest(email, password))
           } else {
               repository.login(LoginRequest(email, password))
           }

           if (result is UiState.Success) {

               if (result.data.Success) {

                   val gson = Gson()
                   val parsedData = gson.fromJson(
                       result.data.Data,
                       InnerLoginWrapper::class.java
                   )

                   val loginResult = parsedData.data.firstOrNull()

                   if (loginResult?.result_status == 1) {

                       _loginState.value = UiState.Success(result.data)

                       // 🔥 Navigate based on role
                       _navigateToManager.value = role == "Manager"

                   } else {
                       _loginState.value =
                           UiState.Error(loginResult?.msg ?: "Wrong details")
                   }

               } else {
                   _loginState.value =
                       UiState.Error(result.data.Message)
               }

           } else if (result is UiState.Error) {
               _loginState.value = result
           }
       }
   }

   */

    /* fun login(email: String, password: String, role: String) {
      viewModelScope.launch {

          _loginState.value = UiState.Loading

          val result = if (role == "Manager") {
              repository.managerLogin(LoginRequest(email, password))
          } else {
              repository.login(LoginRequest(email, password))
          }

          when (result) {

              is UiState.Success -> {

                  val response = result.data

                  if (!response.Success) {
                      _loginState.value =
                          UiState.Error(response.Message ?: "Login failed")
                      return@launch
                  }

                  try {
                      val gson = Gson()

                      if (response.Data.isNullOrEmpty()) {
                          _loginState.value =
                              UiState.Error("Invalid server response")
                          return@launch
                      }

                      val parsedData = gson.fromJson(
                          response.Data,
                          InnerLoginWrapper::class.java
                      )

                      val loginResult = parsedData?.data?.firstOrNull()

                      if (loginResult?.result_status == 1) {

                          _loginState.value = UiState.Success(response)

                          _navigateToManager.value = role == "Manager"

                      } else {
                          _loginState.value =
                              UiState.Error(loginResult?.msg ?: "Wrong details")
                      }

                  } catch (e: Exception) {
                      _loginState.value =
                          UiState.Error("Something went wrong")
                  }
              }

              is UiState.Error -> {
                  _loginState.value =
                      UiState.Error(result.message ?: "Network error")
              }

              else -> {}
          }
      }
  }

  */
  fun login(email: String, password: String, role: String) {
      viewModelScope.launch {
          _loginState.value = UiState.Loading

          val result = if (role == "Manager") {
              repository.managerLogin(LoginRequest(email, password))
          } else {
              repository.login(LoginRequest(email, password))
          }

          when (result) {
              is UiState.Success -> {
                  val response = result.data
                  if (!response.Success) {
                      _loginState.value = UiState.Error(response.Message ?: "Login failed")
                      return@launch
                  }

                  try {
                      val gson = Gson()
                      if (response.Data.isNullOrEmpty()) {
                          _loginState.value = UiState.Error("Invalid server response")
                          return@launch
                      }
                      val parsedData = gson.fromJson(response.Data, InnerLoginWrapper::class.java)
                      val loginResult = parsedData?.data?.firstOrNull()

                      if (loginResult?.result_status == 1) {
                          _loginState.value = UiState.Success(response)

                          if (role == "Manager") {
                              _navigateToManager.value = true
                          } else {
                              _navigateToMain.value = loginResult.DId to (loginResult.Name ?: "Unknown")
                          }
                      } else {
                          _loginState.value = UiState.Error(loginResult?.msg ?: "Wrong details")
                      }

                  } catch (e: Exception) {
                      _loginState.value = UiState.Error("Something went wrong")
                  }
              }

              is UiState.Error -> {
                  _loginState.value = UiState.Error(result.message ?: "Network error")
              }

              else -> {}
          }
      }
  }

    /*fun resetNavigation() {
        _navigateToManager.value = null
    }

     */
    fun resetNavigation() {
        _navigateToManager.value = null
        _navigateToMain.value = null
    }
}


