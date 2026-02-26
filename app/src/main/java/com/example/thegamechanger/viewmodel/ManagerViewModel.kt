package com.example.thegamechanger.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thegamechanger.model.AddDealerOnTableRequest
import com.example.thegamechanger.model.DealerOnTableRequest
import com.example.thegamechanger.model.DealerTable
import com.example.thegamechanger.model.TableMaster
import com.example.thegamechanger.remote.DealerApiLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerViewModel @Inject constructor(
    private val api: DealerApiLogin
) : ViewModel() {

    var dealerList by mutableStateOf<List<DealerTable>>(emptyList())
        private set

    var tableMasters by mutableStateOf<List<TableMaster>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)

  fun loadDashboard(mId: Int) {
      viewModelScope.launch {
          isLoading = true
          try {
              val response = api.getDealerOnTable(
                  DealerOnTableRequest(mId)
              )
              if (response.Success) {
                  dealerList =
                      response.Data.DealerTables
                          .sortedByDescending { it.EntryStatus }

                  tableMasters = response.Data.TableMasters
              }

          } catch (e: Exception) {
              e.printStackTrace()
          }
          isLoading = false

      }
  }
    fun assignDealer(
        mId: Int,
        dealerId: Int,
        tableId: Int
    ) {
        viewModelScope.launch {
            try {
                val response = api.addDealerOnTable(
                    AddDealerOnTableRequest(
                        MId = mId,
                        DId = dealerId,
                        TbId = tableId,
                        Coin = 0,
                        IsAdd = 1,
                        dtbId = 0
                    )
                )
                if (response.Success) {
                    // Refresh Dashboard
                    loadDashboard(mId)

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun removeDealer(
        mId: Int,
        dealerId: Int,
        tableId: Int,
        coin: Int,
        dtbId: Int

    ) {
        viewModelScope.launch {
            try {

                val response = api.addDealerOnTable(

                    AddDealerOnTableRequest(
                        MId = mId,
                        DId = dealerId,
                        TbId = tableId,
                        Coin = coin,
                        IsAdd = 0,
                        dtbId = dtbId

                    )

                )
                if (response.Success) {

                    loadDashboard(mId)

                }

            } catch (e: Exception) {

                e.printStackTrace()

            }

        }

    }

}