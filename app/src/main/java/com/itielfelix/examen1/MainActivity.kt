package com.itielfelix.examen1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itielfelix.examen1.ui.theme.Examen1Theme

var appColor = androidx.compose.ui.graphics.Color.Red
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Examen1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UIMenu()

                }
            }
        }
    }
}
@Preview
@Composable
fun UIMenu() {
    val rememberColor by remember{ mutableStateOf(appColor) }
    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = rememberColor)
    val thisContext = LocalContext.current

    Box() {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            val logo = if (isSystemInDarkTheme()) R.drawable.light_logo else R.drawable.dark_logo
            Box(
                Modifier.padding(50.dp)
                    .height(100.dp)){
                Image(painterResource(logo), contentDescription = "light logo", Modifier.size(300.dp))
            }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
                Spacer(modifier = Modifier.padding(15.dp))
                Button(onClick = {
                    val intent = Intent(thisContext, GameActivity::class.java)
                    thisContext.startActivity(intent)

                }, shape = RoundedCornerShape(50), colors = buttonColors) {
                    Text("Play!")
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "PLay")
                }
                Spacer(modifier = Modifier.padding(15.dp))
                Button(onClick = {
                    val intent = Intent(thisContext, InstructionsActivity::class.java)
                    thisContext.startActivity(intent)
                }, shape = RoundedCornerShape(50), colors = buttonColors)
                {
                    Text("Instructions")
                    Icon(imageVector = Icons.Default.FileCopy, contentDescription = "PLay")
                }
                Spacer(modifier = Modifier.padding(15.dp))
                Button(onClick = {
                    val intent = Intent(thisContext, HistoryActivity::class.java)
                    thisContext.startActivity(intent)
                }, shape = RoundedCornerShape(50), colors = buttonColors) {
                    Text("View Score")
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Score")
                }

            }
            Row(Modifier.padding(top = 40.dp)) {
                Box(contentAlignment = Alignment.BottomStart) {
                    Image(painterResource(id = R.drawable.maincards), contentDescription = "cards")
                }
            }
        }
        

    }
}
