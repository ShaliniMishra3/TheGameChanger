package com.example.thegamechanger.remote

import android.content.Context
class SessionManager(context: Context) {
    private val prefs=context.getSharedPreferences("game_session",Context.MODE_PRIVATE)
    fun saveLogin(dealerId:Int, dealerName:String, role:String){
        prefs.edit()
            .putBoolean("isLoggedIn", true)
            .putInt("dealerId", dealerId)
            .putString("dealerName", dealerName)
            .putString("role", role)
            .apply()
    }
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("isLoggedIn", false)
    }
    fun saveTwid(twid: Int) {
        prefs.edit().putInt("game_twid", twid).apply()
    }

    fun getTwid(): Int {
        return prefs.getInt("game_twid", 0)
    }

    fun clearTwid() {
        prefs.edit().remove("game_twid").apply()
    }
    fun getDealerId(): Int {
        return prefs.getInt("dealerId", 0)
    }
    fun getDealerName(): String {
        return prefs.getString("dealerName", "") ?: ""
    }
    fun getRole(): String {
        return prefs.getString("role", "") ?: ""
    }
    fun logout() {
        prefs.edit().clear().apply()
    }
}