package com.itielfelix.examen1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itielfelix.examen1.ui.theme.Examen1Theme

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
    val thisContext = LocalContext.current
    val roundedCorners = Modifier
        .heightIn(50.dp)
        .clip(RoundedCornerShape(40.dp))
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(15.dp))
            Button(onClick = {
                val intent = Intent(thisContext, GameActivity::class.java);
                thisContext.startActivity(intent)

            }, shape = RoundedCornerShape(50)) {
                Text("Play!")
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "PLay")
            }
            Spacer(modifier = Modifier.padding(15.dp))
            Button(onClick = {
                val intent = Intent(thisContext, InstructionsActivity::class.java);
                thisContext.startActivity(intent)},shape = RoundedCornerShape(50))
            {
                Text("Instructions")
                Icon(imageVector = Icons.Default.FileCopy, contentDescription = "PLay")
            }
            Spacer(modifier = Modifier.padding(15.dp))
            Button(onClick = {}, shape = RoundedCornerShape(50)) {
                 Text("View Score")
                Icon(imageVector = Icons.Default.Search, contentDescription = "Score" )
            }

        }
    }
}
