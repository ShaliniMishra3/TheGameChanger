package com.example.thegamechanger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    private val _navigateToHome= MutableLiveData(false)
    val navigateToHome: LiveData<Boolean> = _navigateToHome

    fun onLoginClicked(email:String , password:String){
        //simple validation
        if(email.isNotEmpty() && password.isNotEmpty()){
            _navigateToHome.value=true
        }
    }
}