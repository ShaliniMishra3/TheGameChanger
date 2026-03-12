package com.example.thegamechanger.model

data class ChangePasswordRequest(
      val Email:String,
      val OldPassword:String,
      val NewPassword:String,
      val Tp:String
)