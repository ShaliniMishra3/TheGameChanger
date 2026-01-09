package com.example.thegamechanger.ui.theme

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddPersonScreen(
    onBack:()->Unit
){
    val dealers=remember {
        mutableStateListOf("Roopesh","Saini","Aman","Arpit")
    }

    var showDialog by remember { mutableStateOf(false) }
    var selectedDealer by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(PokerOrangeTop,PokerOrangeBottom))
            )
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        //-------HEADER
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text="Add Person",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text="Back",
                color=PokerBlack,
                modifier = Modifier.clickable{onBack()}
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        //--------DEALER LIST----
        dealers.forEach { dealer->
            DealerRow(
               dealerName =dealer,
               onAddClick={
                 selectedDealer=dealer
                 showDialog=true
              }
            )
        }

    }

    if(showDialog && selectedDealer !=null){
        AddDealerDialog(
            dealer=selectedDealer!!,
            onDismiss={showDialog=false},
            onAdd={
                showDialog=false
            }
        )
    }
}
@Composable
fun DealerRow(
    dealerName:String,
    onAddClick:()->Unit
){
    Card(
        modifier= Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape= RoundedCornerShape(14.dp),
        colors= CardDefaults.cardColors(containerColor = PokerWhite),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text="Dealer $dealerName",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier= Modifier
                    .size(34.dp)
                    .background(PokerBlack,RoundedCornerShape(8.dp))
                    .clickable{onAddClick()},
                contentAlignment = Alignment.Center
            ){
                Text("+",color=PokerWhite, fontSize = 20.sp)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDealerDialog(
    dealer:String,
    onDismiss:()->Unit,
    onAdd:(String)->Unit
){
    var name by remember { mutableStateOf("") }

    BasicAlertDialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PokerBlack),
            modifier = Modifier.padding(24.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "Add Dealer",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PokerWhite
                )

                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Dealer Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = androidx.compose.material3.TextFieldDefaults.colors(
                        focusedTextColor = PokerWhite,
                        unfocusedTextColor = PokerWhite,
                        focusedIndicatorColor = PokerWhite,
                        unfocusedIndicatorColor = PokerGray,
                        focusedLabelColor = PokerWhite,
                        unfocusedLabelColor = PokerGray,
                        cursorColor = PokerWhite,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(22.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cancel",
                        color = PokerGray,
                        modifier = Modifier
                            .clickable { onDismiss() }
                            .padding(end = 16.dp)
                    )
                    Button(
                        onClick = {
                            if (name.isNotBlank()) onAdd(name)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PokerOrangeTop,
                            contentColor = PokerBlack
                        )
                    ) {
                        Text("Add", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

}
