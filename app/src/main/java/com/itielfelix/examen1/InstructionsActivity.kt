package com.itielfelix.examen1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.google.accompanist.pager.HorizontalPager
import com.itielfelix.examen1.ui.theme.Examen1Theme
var instructionsHand = mutableListOf<String>()
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


@SuppressLint("MutableCollectionMutableState")
@Composable
fun UIBuilding() {
    val cardModifier = Modifier
        .height(200.dp)
        .width(130.dp)
        .padding(2.dp)
        .clip(
            RoundedCornerShape(8)
        )
        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8))
    val instructionCards by remember{ mutableStateOf(instructionsHand.toMutableStateList()) }
    var stand by remember{ mutableStateOf(false) }
    val thisContext = LocalContext.current
    Box {Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(ScrollState(0)), horizontalAlignment = Alignment.CenterHorizontally) {
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
        Text("If you are ok with those cards, you can click \"Stand\". This make the game end and the player is determined")
        Button(onClick = {
            stand = true
        }, enabled= !stand, shape = RoundedCornerShape(50)
        ) {
            Text("Stand")
        }
        Text("Croupier Points")
        Text("")
        Text("Player points")
        Text(calculatePoints(listCards = instructionCards).toString())
        HorizontalPager(count = 3, state = ) {

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
                //instructionCards.clear()
                variableCards = allCards.toMutableList()
            },
            backgroundColor = Color(99, 5, 220)) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", Modifier.size(30.dp))
        }
    }


}
