package com.itielfelix.examen1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itielfelix.examen1.ui.theme.Examen1Theme


lateinit var history:DatabaseHelper
class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Examen1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    history = DatabaseHelper(this)
                    UIHistoryBuilding()
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun UIHistoryBuilding() {
        Examen1Theme {
            AllUI()
        }
    }

    @Composable
    fun AllUI() {
        val data = history.listData().toTypedArray()
        Column(Modifier.verticalScroll(ScrollState(0))) {
            for(row in data){
                GenerateCard(row)
            }
        }
    }

    @Composable
    fun GenerateCard(row: String) {
        val splittedRow = row.split("/")

        val hours = splittedRow[4].split(" ")
        var expanded by remember{ mutableStateOf(false) }
        Card(
            elevation = 10.dp,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    expanded = !expanded
                },
            shape = RoundedCornerShape(20.dp)
        ){
            val columnsWidth = 70.dp
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Column {
                        Text(text = splittedRow[0], fontWeight = Bold, fontStyle = FontStyle.Italic, fontSize = 20.sp)
                    }
                        Column(Modifier.width(columnsWidth), horizontalAlignment = Alignment.CenterHorizontally){
                            if(splittedRow[3] == "Player") Text(">Player<", fontWeight = ExtraBold, color = Color.Red) else Text(text = "Player")
                        }
                    Column(Modifier.width(columnsWidth+20.dp), horizontalAlignment = Alignment.CenterHorizontally){
                            if(splittedRow[3] == "Croupier") Text(">Croupier<", fontWeight = ExtraBold, color = Color.Red) else Text(text = "Croupier")
                        }
                    Column(Modifier.width(columnsWidth-10.dp), horizontalAlignment = Alignment.CenterHorizontally){
                            if(splittedRow[3] == "Draw") Text(">Draw<", fontWeight = ExtraBold, color = Color.Red) else Text(text = "        ")
                        }
                    Column(Modifier.width(20.dp), horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = "Date", fontSize = 10.sp)
                        Text(text = hours[0], fontSize = 8.sp)
                        Text(text = hours[1], fontSize = 8.sp)
                }
                }
                if(expanded){
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                        Row(Modifier.fillMaxWidth()) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally){
                                Text(text = "Croupier reached ${calculatePoints(listCards = splitArray(splittedRow[2]))} points with these cards.")
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                                    RenderizeCards(cardString = splittedRow[2])
                                }
                            }
                        }

                        Row(Modifier.fillMaxWidth()) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally){
                                Text(text = "Player reached ${calculatePoints(listCards = splitArray(splittedRow[1]))} points with these cards.")
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                                    RenderizeCards(cardString = splittedRow[1])
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun RenderizeCards(cardString:String){

        val width = 40
        val height = (width*1.53).dp
        val cardModifier = Modifier
            .height(height)
            .width(width.dp)
            .padding(2.dp)
            .clip(
                RoundedCornerShape(8)
            )
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8))
        val thisContext = LocalContext.current
        val allCardsList = cardString.split(",")
        val cardsList = mutableListOf<String>()
        cardsList.addAll(allCardsList)
        cardsList.removeLast()
        Toast.makeText(thisContext,cardsList.toString(), Toast.LENGTH_SHORT).show()
        for(item in cardsList){
         com.itielfelix.examen1.GenerateCard(thisContext = thisContext, card = item, modifier = cardModifier)
        }
    }

    fun splitArray(rowCards:String):MutableList<String>{
        val allCards = rowCards.split(",")
        val cardsList = mutableListOf<String>()
        cardsList.addAll(allCards)
        cardsList.removeLast()
        return cardsList
    }
}