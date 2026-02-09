package com.example.thegamechanger.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GameViewModel(application: Application): AndroidViewModel(application) {
    private val prefs=application.getSharedPreferences("game_data",Context.MODE_PRIVATE)
    private val gson= Gson()

    val players= mutableStateListOf<Player>()
        //Player("Dealer",15000)
    init {
        loadData()
    }
    private fun saveData(){
        val json=gson.toJson(players.toList())
        prefs.edit().putString("players_list",json).apply()
    }
    private fun loadData(){
        val json=prefs.getString("players_list",null)
        if(json!=null){
            val type=object: TypeToken<List<Player>>(){}.type
            val savedList:List<Player> = gson.fromJson(json,type)
            players.clear()
            players.addAll(savedList)
        }else{
            players.add(Player("Dealer",15000))
        }
    }
    fun addOrUpdatePlayer(name:String, amount:Int){
        val index=players.indexOfFirst{it.name==name}
        if(index!=-1){
            val currentPlayer=players[index]
            players[index]=currentPlayer.copy(amount=currentPlayer.amount+amount)
        }else{
            players.add(Player(name,amount))
        }
        saveData()
    }

    fun updateDealerCommission(winAmount: Int) {
        val dealerIndex = players.indexOfFirst { it.name == "Dealer" }
        if (dealerIndex != -1) {
            val commission = (winAmount * 0.10).toInt()
            val dealer = players[dealerIndex]
            players[dealerIndex] = dealer.copy(
                amount = dealer.amount + commission
            )
        }
        saveData()
    }
    fun removePlayer(player:Player){
        if(player.name!="Dealer"){
            players.removeIf{it.name == player.name}
            saveData()
        }
    }
}