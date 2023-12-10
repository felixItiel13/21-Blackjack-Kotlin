package com.itielfelix.examen1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.itielfelix.examen1.ui.theme.Examen1Theme

var instructionsHand = mutableListOf<String>()
var croupierHand = mutableListOf<String>()
class InstructionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Examen1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    instructionsHand.add(variableCards.removeLast())
                    instructionsHand.add(variableCards.removeLast())
                    croupierHand.add(variableCards.removeLast())
                    croupierHand.add(variableCards.removeLast())
                    DefaultPreview()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Examen1Theme {
        UIBuilding()
    }
}


@OptIn(ExperimentalPagerApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun UIBuilding() {
    val width = 80
    val height = (width*1.53).dp
    val cardModifier = Modifier
        .height(height)
        .width(width.dp)
        .padding(2.dp)
        .clip(
            RoundedCornerShape(8)
        )
        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8))
//    val cardModifier = Modifier
//        .height(200.dp)
//        .width(130.dp)
//        .padding(2.dp)
//        .clip(
//            RoundedCornerShape(8)
//        )
//        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8))
    val instructionCards by remember{ mutableStateOf(instructionsHand.toMutableStateList()) }
    var stand by remember{ mutableStateOf(false) }
    val thisContext = LocalContext.current
    Box {Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Instructions", fontSize = 38.sp, fontWeight = FontWeight.ExtraBold, fontStyle = FontStyle.Italic)
            Text("General Rules",fontSize = 28.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Justify)
            Text("Initially you will have 2 cards. These cards have a number in the top left side.")
            Text("All of those number are points. If you have more points than Croupier, without exceeding 21 points, you will have won ")

            Text("Gameplay Rules",fontSize = 28.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Justify)
            Text("To draw a card, click the next button")
            Button(onClick = {
                if(variableCards.size>0) {
                    instructionCards.add(variableCards.removeLast())
                }
            }, shape = RoundedCornerShape(50)
            ) {
                Text("Draw Card")
            }
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy((-34).dp)) {
            items(instructionCards) { card ->
                GenerateCard(thisContext = LocalContext.current, card = card,cardModifier)
            }
        }
        Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text("If you are ok with those cards, you can click \"Stand\". This make the game end and the player is determined")
            Button(
                onClick = {
                    stand = true
                }, enabled = !stand, shape = RoundedCornerShape(50)
            ) {
                Text("Stand")
            }
            Text("In that moment, the croupier cards will be revealed. In this case, there is 4 cases for choose the winner")
            val pagerState = rememberPagerState()
            val pagerCount = 4
            HorizontalPager(count = pagerCount, state = pagerState) { page ->
                Box(Modifier.size(500.dp, 600.dp)){
                    SelectPage(page = page, cardModifier = cardModifier, thisContext = thisContext)
                }
            }
            Row(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)

                    )
                }
            }

        }
    }
        FloatingActionButton(
            modifier = Modifier
                .padding(all = 20.dp)
                .size(80.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = {
                thisContext.startActivity(Intent(thisContext,MainActivity::class.java))
                instructionsHand.clear()
                croupierHand.clear()
                variableCards = allCards.toMutableList()
            },
            backgroundColor = Color(99, 5, 220)) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", Modifier.size(30.dp))
        }
    }

}
@Composable
fun SelectPage(page: Int, cardModifier: Modifier, thisContext: Context){
    val width = 80
    val height = (width*1.53).dp
    val cardModifier = Modifier
        .height(height)
        .width(width.dp)
        .padding(2.dp)
        .clip(
            RoundedCornerShape(8)
        )
        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8))
    val spacerTop = Spacer(Modifier.padding(10.dp))
    val spacerCards = Spacer(Modifier.padding(10.dp))
    val spacerText = Spacer(Modifier.padding(20.dp))
    val spacer2 = Spacer(Modifier.padding(10.dp))
    val columnModifier =
    when(page){
        0 ->{
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                spacerTop
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Case #1. Croupier exceed 21 points.", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(18.dp), textAlign = TextAlign.Center)
                    Text(text = "Note: If croupier has less than 17 points with their cards, he is obligated to take another card.", fontStyle = FontStyle.Italic, fontSize = 10.sp)
                    Text(text = "In this case, croupier has more than 21 points. If player has less or equal to 21 points, he will win.")
                    Spacer(Modifier.padding(20.dp))
                    Row() {
                        GenerateCard(thisContext = thisContext, card = "cq", modifier = cardModifier)
                        GenerateCard(thisContext = thisContext, card = "d6", modifier = cardModifier)
                        GenerateCard(thisContext = thisContext, card = "d8", modifier = cardModifier)
                    }
                    Text("Croupier points: ${calculatePoints(listCards = arrayListOf("cq","d6","d8"))}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Croupier cards")
                }
                Spacer(Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Player cards")
                    Text("Player Points: ${calculatePoints(listCards = arrayListOf("s4","dq"))}" , fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Row() {
                        GenerateCard(thisContext = thisContext, card = "s4", modifier = cardModifier)
                        GenerateCard(thisContext = thisContext, card = "dq", modifier = cardModifier)
                    }
                }
            }
        }
        1 ->{
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Case #2. Player exceed 21 points.", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(18.dp), textAlign = TextAlign.Center)
                    Text(text = "In this case, player has more than 21 cards. If croupier has less or equal to 21 points, he will win.")
                    Spacer(Modifier.padding(20.dp))
                    Row() {
                        GenerateCard(thisContext = thisContext, card = "cq", modifier = cardModifier)
                        GenerateCard(thisContext = thisContext, card = "d3", modifier = cardModifier)
                        GenerateCard(thisContext = thisContext, card = "d8", modifier = cardModifier)
                    }
                    Text("Croupier points: ${calculatePoints(listCards = arrayListOf("cq","d3","d8"))}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Croupier cards")
                }
                Spacer(Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Player cards")
                    Text("Player Points: ${calculatePoints(listCards = arrayListOf("s4","dq","ck"))}" , fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Row() {
                        GenerateCard(thisContext = thisContext, card = "s4", modifier = cardModifier)
                        GenerateCard(thisContext = thisContext, card = "dq", modifier = cardModifier)
                        GenerateCard(thisContext = thisContext, card = "ck", modifier = cardModifier)
                    }
                }
            }
        }
        2 ->{
            Box(){
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = "Case #3. Everyone has 21 points or less.", fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.padding(18.dp))
                        Text(text = "In this case, the winner will be determined by the points of each player. The player who is closest to 21 points will win.")
                        Spacer(Modifier.padding(20.dp))
                        Row() {
                            GenerateCard(thisContext = thisContext, card = "cq", modifier = cardModifier)
                            GenerateCard(thisContext = thisContext, card = "d3", modifier = cardModifier)
                            GenerateCard(thisContext = thisContext, card = "d5", modifier = cardModifier)
                        }
                        Text("Croupier points: ${calculatePoints(listCards = arrayListOf("c3","d3","d4"))}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Croupier cards")
                    }
                    Spacer(Modifier.padding(20.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Player cards")
                        Text("Player Points: ${calculatePoints(listCards = arrayListOf("d8","s3","hk"))}" , fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Row() {
                            GenerateCard(thisContext = thisContext, card = "d8", modifier = cardModifier)
                            GenerateCard(thisContext = thisContext, card = "s3", modifier = cardModifier)
                            GenerateCard(thisContext = thisContext, card = "hk", modifier = cardModifier)
                        }
                    }
                }
            }
        }
        3 ->{
            Box(){
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = "Case #4. Draw", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top =18.dp, bottom = 45.dp))
                        Text(text = "If player and croupier has the same amount of points, the game will be declared as a draw.", modifier = Modifier.padding(start = 5.dp))
                        Spacer(Modifier.padding(20.dp))
                        Row() {
                            GenerateCard(thisContext = thisContext, card = "cq", modifier = cardModifier)
                            GenerateCard(thisContext = thisContext, card = "d3", modifier = cardModifier)
                            GenerateCard(thisContext = thisContext, card = "s5", modifier = cardModifier)
                        }
                        Text("Croupier points: ${calculatePoints(listCards = arrayListOf("c3","d3","d4"))}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Croupier cards")
                    }
                    Spacer(Modifier.padding(20.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Player cards")
                        Text("Player Points: ${calculatePoints(listCards = arrayListOf("d5","s3","hk"))}" , fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Row() {
                            GenerateCard(thisContext = thisContext, card = "d5", modifier = cardModifier)
                            GenerateCard(thisContext = thisContext, card = "s3", modifier = cardModifier)
                            GenerateCard(thisContext = thisContext, card = "hk", modifier = cardModifier)
                        }
                    }
                }
            }
        }
        else ->{}
    }
}
