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
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.thegamechanger.model.AddPlayerResult
import com.example.thegamechanger.model.AddPlayerTableRequest
import com.example.thegamechanger.model.GameStartRequest
import com.example.thegamechanger.model.GameStartResponse
import com.example.thegamechanger.model.PlayerOnTableItem
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.emptyList

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



    private val _tableName = mutableStateOf("")
    val tableName: State<String> = _tableName
   // private val _playersOnTable = mutableStateListOf<PlayerOnTableItem>()
   // val playersOnTable: SnapshotStateList<PlayerOnTableItem> = _playersOnTable
   private val _playersOnTable =
       MutableStateFlow<List<PlayerOnTableItem>>(emptyList())

    val playersOnTable = _playersOnTable.asStateFlow()
    private val _availablePlayers =
        MutableStateFlow<UiState<List<PlayerDto>>>(UiState.Idle)
    val availablePlayers = _availablePlayers.asStateFlow()
    private val _gameState = MutableStateFlow<UiState<GameStartResponse>?>(null)
    val gameState = _gameState
    var currentTwid = 0
   private val _tablePlayers =
       MutableStateFlow<List<AddPlayerResult>>(emptyList())
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
    var tableId = mutableStateOf(0)
    fun setTableId(id: Int) {
        tableId.value = id
    }


    fun fetchPlayerOnTable(dealerId: Int) {

        viewModelScope.launch {

            val result = repository.getPlayerOnTable(dealerId)

            if (result is UiState.Success) {

                val dealerTables = result.data.Data?.DealerTables ?: emptyList()

                _playersOnTable.value = dealerTables

                val dashboard = result.data.Data?.DealerDashboard?.firstOrNull()

                if (dashboard != null) {

                    _dealerName.value = dashboard.DealerName

                    _tableName.value = dashboard.TableName

                    _dealerId.value = dashboard.DId

                    tableId.value = dashboard.tbId

                }

            }

        }
    }
    /*fun fetchPlayerOnTable(dealerId: Int) {

        viewModelScope.launch {

            val result = repository.getPlayerOnTable(dealerId)

            if (result is UiState.Success) {

                _playersOnTable.clear()

                if (result.data.isNotEmpty()) {

                    val first = result.data.first()

                    _dealerName.value = first.DealerName.toString()
                    _tableName.value = first.TableName.toString()
                    _dealerId.value = first.DId
                    _tableId.value = first.tbId
                }

                _playersOnTable.addAll(result.data)

                println("TABLE SIZE = ${_playersOnTable.size}")
            }
        }
    }

     */
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
    fun setDealer(dealerId:Int,dealerName:String){

        _dealerId.value = dealerId
        _dealerName.value = dealerName

    }
    fun startGame() {

        viewModelScope.launch {

            _gameState.value = UiState.Loading

            val result = repository.gameStart(
                GameStartRequest(
                    DId = dealerId.value,
                    tbId = tableId.value,
                    IsStart = 1,
                    WinningCoin = "0",
                    Twid = 0
                )
            )

            when(result){

                is UiState.Success ->{

                    currentTwid = result.data.Data.Twid

                    _gameState.value = result
                }

                is UiState.Error ->{
                    _gameState.value = result
                }

                else ->{}
            }
        }
    }
    fun stopGame(winningAmount:String){

        viewModelScope.launch {

            _gameState.value = UiState.Loading

            val result = repository.gameStart(
                GameStartRequest(
                    DId = dealerId.value,
                    tbId = tableId.value,
                    IsStart = 0,
                    WinningCoin = winningAmount,
                    Twid = currentTwid
                )
            )

            _gameState.value = result
        }
    }
}
