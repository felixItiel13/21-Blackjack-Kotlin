package com.itielfelix.examen1

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Composable
fun UIMenu() {
    val roundedCorners = Modifier.heightIn(50.dp).clip(RoundedCornerShape(40.dp))
    Column(Modifier.fillMaxSize()){
        Column(Modifier.fillMaxWidth()) {
            Button(onClick = {},roundedCorners.padding(10.dp)) {
                Text("Play!")
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "PLay")
            }
            Button(onClick = {},roundedCorners) {
                Text("Instuctions")
                Icon(imageVector = Icons.Default.FileCopy, contentDescription = "PLay")
            }
            Button(onClick = {},roundedCorners) {
                 Text("View Score")
                Icon(imageVector = Icons.Default.Search, contentDescription = "Score" )
            }

        }
    }
}
