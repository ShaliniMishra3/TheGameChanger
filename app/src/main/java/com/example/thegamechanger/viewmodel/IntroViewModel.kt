package com.example.thegamechanger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IntroViewModel : ViewModel(){
    private val _navigateToLogin= MutableLiveData(false)
    val navigateToLogin: LiveData<Boolean> = _navigateToLogin

    fun onLetStartClicked(){
        _navigateToLogin.value=true
    }
}