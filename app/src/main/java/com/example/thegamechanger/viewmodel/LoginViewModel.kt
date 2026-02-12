package com.example.thegamechanger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private val _navigateToHome= MutableLiveData(false)
    private val _navigateToManager= MutableLiveData(false)
    val navigateToHome: LiveData<Boolean> = _navigateToHome

    fun onLoginClicked(email:String , password:String){
        /*
       if(email.isNotEmpty() && password.isNotEmpty()){
            _navigateToHome.value=true
        }
        */

        if (email == "admin@poker.com" && password == "admin123") {
            _navigateToManager.value = true
        } else {
            _navigateToHome.value = true
        }
    }
}