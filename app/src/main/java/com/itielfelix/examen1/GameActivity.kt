package com.itielfelix.examen1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.RoundedCorner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.itielfelix.examen1.ui.theme.Examen1Theme
var allCards  = mutableListOf("ha","h2","h3","h4","h5","h6","h7","h8","h9","h10","hj","hq","hk"
    ,"sa","s2","s3","s4","s5","s6","s7","s8","s9","s10","sj","sq","sk"
    ,"ca","c2","c3","c4","c5","c6","c7","c8","c9","c10","cj","cq","ck"
    ,"da","d2","d3","d4","d5","d6","d7","d8","d9","d10","dj","dq","dk")
var variableCards = allCards.toMutableList()
var playerHand = mutableListOf<String>()
var croupier = mutableListOf<String>()
var first = true
class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Examen1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    variableCards.shuffle()
                    playerHand.add(variableCards.removeLast())
                    playerHand.add(variableCards.removeLast())
                    croupier.add(variableCards.removeLast())
                    croupier.add(variableCards.removeLast())
                    GameUI()
                }
            }
        }
    }


    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("DiscouragedApi")
    @Composable
    fun GameUI() {
        var winner by rememberSaveable{
            mutableStateOf(false)
        }
        var cardModifier = Modifier
            .height(200.dp)
            .width(130.dp)
            .padding(2.dp)
            .clip(
                RoundedCornerShape(8)
            )
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8),)
        val thisContext = LocalContext.current
        val playerCards = remember { playerHand.toMutableStateList() }
        val croupierCards = remember { croupier.toMutableStateList() }
        var numCards by remember{ mutableStateOf( playerCards.size)}
        var points by remember{ mutableStateOf(0) }
        var reached21Points by remember { mutableStateOf(false)}
        var stand by remember { mutableStateOf(false)}

        var reset by remember { mutableStateOf(false)}
        points = calculatePoints(playerCards)
        reached21Points = !(points>21 || stand)
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
            Row(horizontalArrangement = Arrangement.Center){
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
                Row(Modifier.horizontalScroll(ScrollState(0)),horizontalArrangement = Arrangement.spacedBy((-30).dp)) {
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
                    if (stand) {
                        if (calculatePoints(croupierCards) < 17) croupierCards.add(variableCards.removeLast())
                        if (croupierCards.size > 2){
                            Box(cardModifier) {
                                Image(
                                    painterResource(
                                        id = thisContext.resources.getIdentifier(
                                            croupierCards[2],
                                            "drawable",
                                            thisContext.packageName
                                        )
                                    ),
                                    contentDescription = "back_cover",
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier.height(200.dp)
                                )
                            }
                            if (croupierCards.size > 3){
                                Box(cardModifier) {
                                    androidx.compose.foundation.Image(
                                        androidx.compose.ui.res.painterResource(
                                            id = thisContext.resources.getIdentifier(
                                                croupierCards[3],
                                                "drawable",
                                                thisContext.packageName
                                            )
                                        ),
                                        contentDescription = "back_cover",
                                        contentScale = androidx.compose.ui.layout.ContentScale.FillHeight,
                                        modifier = androidx.compose.ui.Modifier.height(200.dp)
                                    )
                                }

                            }
                        }
                        if(winner == false) {
                            Toast.makeText(
                                thisContext,
                                "Croupier " + calculatePoints(listCards = croupierCards).toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            Toast.makeText(
                                thisContext,
                                "Player " + points.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            Toast.makeText(
                                thisContext,
                                chooseWinner(calculatePoints(listCards = croupierCards), points),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        winner = true
                    }
                }
            }
            Text(calculatePoints(listCards = croupierCards).toString() +" "+ croupierCards.size.toString())
            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    playerCards.add(variableCards.removeLast())
                    numCards = playerCards.size
                },
                    enabled = reached21Points, shape = RoundedCornerShape(50)
                ) {
                    Text("Draw Card")
                }

                Button(onClick = {
                    stand = !stand
                    reached21Points = false
                }, shape = RoundedCornerShape(50),
                    enabled = !stand) {
                    Text(text = "Stand")
                }
            }
            Text(points.toString())
            LazyRow(horizontalArrangement = Arrangement.spacedBy((-34).dp)) {
                items(playerCards) { card ->
                    GenerateCard(thisContext = thisContext, card = card,cardModifier)
                }
            }
            Row(){
                Button(onClick = {
                    playerHand.clear()
                    croupier.clear()
                finish()
                }, shape = RoundedCornerShape(50)) {
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
                    reached21Points = false
                    stand = false
                    winner = false
                }, shape = RoundedCornerShape(50)) {
                    Text(text = "Reset")
                }
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun GenerateCard(thisContext: Context, card: String, cardModifier:Modifier) {
        var visible by remember { mutableStateOf(false) }
            Box(cardModifier){
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
}
@Composable
fun calculatePoints(listCards:MutableList<String>):Int{
    val thisContext = LocalContext.current
    var points = 0
    var listOfLetterCards = mutableListOf<String>()
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
    listOfLetterCards.forEach{
        if((points+11) >21){
            points+=1
        }else
            points+=11
    }
    return points
}

fun chooseWinner(croupier:Int, player:Int):String{
    var winner = ""
    if(player>21){
        winner = "Croupier"
    }else{
        if (croupier>21){
            winner = "Player"
        }
        else{
            if(croupier>player) winner = "Croupier"
            else winner = "Player"
        }
    }
    return "$winner has won!"
}