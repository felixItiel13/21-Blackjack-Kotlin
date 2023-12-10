package com.itielfelix.examen1

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.itielfelix.examen1.ui.theme.Examen1Theme
import java.text.SimpleDateFormat
import java.util.*

var allCards  = mutableListOf("h2","h3","h4","h5","h6","h7","h8","h9","h10","hj","hq","hk"
    ,"sa","s2","s3","s4","s5","s6","s7","s8","s9","s10","sj","sq","sk"
    ,"ca","c2","c3","c4","c5","c6","c7","c8","c9","c10","cj","cq","ck"
    ,"da","d2","d3","d4","d5","d6","d7","d8","d9","dj","dq","ha","d10","dk")
var variableCards = allCards.toMutableList()
var playerHand = mutableListOf<String>()
var croupier = mutableListOf<String>()
var limit = 21
class GameActivity : ComponentActivity() {
lateinit var history:DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Examen1Theme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    variableCards.shuffle()
                    playerHand.add(variableCards.removeLast())
                    playerHand.add(variableCards.removeLast())
                    croupier.add(variableCards.removeLast())
                    croupier.add(variableCards.removeLast())
                    history = DatabaseHelper(this)
                    GameUI()
                }
            }
        }
    }


    @SuppressLint("DiscouragedApi", "SimpleDateFormat")
    @Composable
    fun GameUI() {
        var limitPoints by remember{mutableStateOf(limit)}
        val buttonColors = ButtonDefaults.buttonColors(backgroundColor = appColor)
        var winner by rememberSaveable{
            mutableStateOf(false)
        }
        val cardModifier = Modifier
            .height(200.dp)
            .width(130.dp)
            .padding(2.dp)
            .clip(
                RoundedCornerShape(8)
            )
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8))
        val thisContext = LocalContext.current

        val playerCards = remember { playerHand.toMutableStateList() } //Remember value of player hand of cards
        val croupierCards = remember { croupier.toMutableStateList() } //Croupier hand of cards
        var numCards by remember{ mutableStateOf( playerCards.size)} //Number of cards in playersHand
        var points by remember{ mutableStateOf(0) } //Points earned
        var reachedPoints by remember { mutableStateOf(false)} //True if 21 points has been reached
        var stand by remember { mutableStateOf(false)} //True if player dont want to draw cards

        //Determine if 21 points has been reached
        points = calculatePoints(playerCards)
        reachedPoints = !(points>limit || stand)

        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
            Row(Modifier.horizontalScroll(ScrollState(0)),horizontalArrangement = Arrangement.Center){
                Box(cardModifier){
                    Image(
                        painterResource(
                            id = thisContext.resources.getIdentifier(
                                croupierCards[0],
                                "drawable",
                                thisContext.packageName
                            )
                        ),
                        contentDescription = "back_cover",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.height(200.dp)

                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy((-30).dp)) {
                    Box(cardModifier) {
                        Image(
                            painterResource(
                                id = thisContext.resources.getIdentifier(
                                    if (!stand) "back_cover" else croupierCards[1],
                                    "drawable",
                                    thisContext.packageName
                                )
                            ),
                            contentDescription = "back_cover",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.height(200.dp)
                        )
                    }
                    //If player wants to stand we start the "croupier turn"
                    //If croupier has less than 17 points, he is obliged to take a card.
                    if (stand) {
                        while (calculatePoints(croupierCards) < 17) {
                            croupierCards.add(variableCards.removeLast())
                        }
                        //Showing all croupier cards
                        for (i in 2 until croupierCards.size)
                        {
                            Box(cardModifier) {
                            Image(
                                painterResource(
                                    id = thisContext.resources.getIdentifier(
                                        croupierCards[i],
                                        "drawable",
                                        thisContext.packageName
                                    )
                                ),
                                contentDescription = "back_cover",
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier.height(200.dp)
                            )
                        }
                        }
                        if(!winner) {
                            val winnerName = chooseWinner(calculatePoints(listCards = croupierCards),points)
                            Toast.makeText(
                                thisContext,if(winnerName=="Draw") "It was a draw!" else "$winnerName has Won!",
                                Toast.LENGTH_SHORT
                            ).show()

                            val time = Calendar.getInstance().time
                            val formatter = SimpleDateFormat("MM-dd HH:mm")
                            val current = formatter.format(time)
                            var savedPlayerCards = ""
                            var savedCroupierCards = ""
                            for(item in playerCards){
                                savedPlayerCards+= "$item,"
                            }
                            for(item in croupierCards){
                                savedCroupierCards+= "$item,"
                            }
                            history.addRow(savedPlayerCards, savedCroupierCards,
                                chooseWinner(calculatePoints(listCards = croupierCards),points), current.toString())
                        }
                        winner = true
                    }
                }
            }
            Text(calculatePoints(listCards = croupierCards).toString())
            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    playerCards.add(variableCards.removeLast())
                    numCards = playerCards.size
                }, colors = buttonColors,
                    enabled = reachedPoints, shape = RoundedCornerShape(50)
                ) {
                    Text("Draw Card")
                }

                Button(onClick = {
                    stand = !stand
                    reachedPoints = false
                }, colors = buttonColors, shape = RoundedCornerShape(50),
                    enabled = !stand) {
                    Text(text = "Stand")
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                    IconButton(onClick = { if(limit!=50) {limit+=1; limitPoints = limit}}, enabled = stand) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = "add")
                }
                    Text("$limitPoints")
                    IconButton(onClick = { if(limit!=0) {limit-=1; limitPoints = limit}}, enabled= stand) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "add")
                }
            }
            Text(points.toString())
            LazyRow(horizontalArrangement = Arrangement.spacedBy((-34).dp)) {
                items(playerCards) { card ->
                    GenerateCard(thisContext = thisContext, card = card,cardModifier)
                }
            }
            Row {
                Button(onClick = {
                    playerHand.clear()
                    croupier.clear()
                finish()
                }, colors = buttonColors, shape = RoundedCornerShape(50)) {
                    Text("Exit")
                }
                Button(onClick = {
                    variableCards = allCards.toMutableList()
                    variableCards.shuffle()
                    playerHand.clear()
                    croupierCards.clear()
                    playerCards.clear()
                    playerCards.add(variableCards.removeLast())
                    playerCards.add(variableCards.removeLast())
                    croupierCards.add(variableCards.removeLast())
                    croupierCards.add(variableCards.removeLast())
                    reachedPoints = false
                    stand = false
                    winner = false
                }, colors = buttonColors, shape = RoundedCornerShape(50)) {
                    Text(text = "Reset")
                }
            }
        }
    }


}
@Composable
fun GenerateCard(thisContext: Context, card: String, modifier:Modifier) {
    Box(modifier){
        Image(
            painterResource(
                id = thisContext.resources.getIdentifier(
                    card,
                    "drawable",
                    thisContext.packageName
                )
            ),
            contentDescription = "card",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.height(200.dp)
        )
    }
}
@Composable
fun calculatePoints(listCards:MutableList<String>):Int{
    var points = 0
    val listOfLetterCards = mutableListOf<String>()
    listCards.forEach{
        if(it[1].isDigit()){
            points+=if(it.length<=2){
                        Integer.parseInt(it[1].toString())
                    }else
                        Integer.parseInt(it.substring(1))
        }else{
           if (it[1].toString()=="a"){
               listOfLetterCards.add(it)
           }else {
               points+=10
           }
        }
    }
    listOfLetterCards.forEach{ _ ->
        points += if((points+11) >limit) { 1 } else 11
    }
    return points
}

fun chooseWinner(croupier:Int, player:Int):String{
    val winner:String = if(player>limit){
                            "Croupier"
                        }else{
                            if (croupier>limit){
                                "Player"
                            } else{
                                if(croupier>player)  {
                                    "Croupier"
                                }
                                else {
                                    if (player>croupier){
                                        "Player"
                                    }
                                    else "Draw"
                                }
                            }
                        }

    return winner
}