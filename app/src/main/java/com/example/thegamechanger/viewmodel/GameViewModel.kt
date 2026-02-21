package com.example.thegamechanger.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thegamechanger.UiState
import com.example.thegamechanger.model.PlayerDto
import com.example.thegamechanger.repository.DealerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import com.example.thegamechanger.model.AddPlayerResult
import com.example.thegamechanger.model.AddPlayerTableRequest
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: DealerRepository
) : ViewModel() {

    private val _addPlayerState =
        MutableStateFlow<UiState<AddPlayerResult>>(UiState.Idle)
    val addPlayerState: StateFlow<UiState<AddPlayerResult>> = _addPlayerState
    private val _dealerName = mutableStateOf("")
    val dealerName: State<String> = _dealerName
    private val _dealerId = mutableStateOf(0)
    val dealerId: State<Int> = _dealerId

    private val _tableId = mutableStateOf(0)
    val tableId: State<Int> = _tableId

    private val _tableName = mutableStateOf("")
    val tableName: State<String> = _tableName
    private val _players = mutableStateListOf<Player>()
    val players: List<Player> = _players
    private val _playerOnTableList = mutableStateListOf<Player>()

    private val _availablePlayers =
        MutableStateFlow<UiState<List<PlayerDto>>>(UiState.Idle)
    val availablePlayers = _availablePlayers.asStateFlow()

   /*init {
        _players.add(Player("Dealer", 15000))
    }

    */

   private val _tablePlayers =
       MutableStateFlow<List<AddPlayerResult>>(emptyList())
    val tablePlayers: StateFlow<List<AddPlayerResult>> = _tablePlayers

    private fun addPlayerToTableList(player: AddPlayerResult) {
        _tablePlayers.value = _tablePlayers.value + player
    }


    fun loadPlayers() {
        viewModelScope.launch {
            _availablePlayers.value = UiState.Loading
            _availablePlayers.value = repository.getPlayerList()
        }
    }



    fun clearAddPlayerState() {
        _addPlayerState.value = UiState.Idle
    }
    fun updateDealerCommission(winAmount: Int) {
        val dealerIndex = _players.indexOfFirst {
            it.name == _dealerName.value
        }
        if (dealerIndex != -1) {
            val commission = (winAmount * 0.10).toInt()
            val dealer = _players[dealerIndex]

            _players[dealerIndex] =
                dealer.copy(amount = dealer.amount + commission)
        }
    }
    fun fetchPlayerOnTable(dealerId: Int) {
        viewModelScope.launch {

            val result = repository.getPlayerOnTable(dealerId)
            if (result is UiState.Success) {
                _players.clear()
                if (result.data.isNotEmpty()) {
                    val first = result.data.first()
                    _dealerName.value = first.DealerName
                    _tableName.value = first.TableName
                    _dealerId.value = first.DId
                    _tableId.value = first.tbId
                }

                result.data.forEach { item ->
                    _players.add(
                        Player(
                            pId = item.PId,
                            name = item.PlayerName,
                            amount = item.CoinEntry.toInt()
                        )
                    )
                }
            }
        }
    }

    fun addPlayerToTable(
        dId: Int,
        pId: Int,
        tbId: Int,
        coin: Double,
        isAdd: Int = 1
    ) {
        viewModelScope.launch {

            _addPlayerState.value = UiState.Loading

            try {

                val response = repository.addPlayerToTable(
                    AddPlayerTableRequest(
                        DId = dId,
                        PId = pId,
                        tbId = tbId,
                        Coin = coin,
                        IsAdd = isAdd
                    )
                )

                if (response.isSuccessful && response.body()?.Success == true) {

                    val result = response.body()?.Data?.data

                    if (result != null) {
                        _addPlayerState.value = UiState.Success(result)

                        // 🔥 Refresh table from backend
                        fetchPlayerOnTable(dId)
                        // Remove from available players list
                        removePlayerFromAvailable(pId)

                        // Add to table list
                        addPlayerToTableList(result)

                        if (result.Result_status == 0) {
                            // Refresh table always
                            fetchPlayerOnTable(dId)
                        }
                    }

                } else {
                    _addPlayerState.value =
                        UiState.Error(response.body()?.Message ?: "Unknown error")
                }

            } catch (e: Exception) {
                _addPlayerState.value = UiState.Error(e.message ?: "Something went wrong")
            }
        }
    }
    private fun removePlayerFromAvailable(pId: Int) {
        val current = (_availablePlayers.value as? UiState.Success)?.data?.toMutableList()
        current?.removeAll { it.PId == pId }
        _availablePlayers.value = UiState.Success(current ?: emptyList())
    }

}
