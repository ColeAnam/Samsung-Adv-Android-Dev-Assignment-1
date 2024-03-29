package com.example.assignment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment1.datastore.StoreStudentInfo
import com.example.assignment1.ui.theme.Assignment1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    // context
    val context = LocalContext.current
    // scope
    val scope = rememberCoroutineScope()
    // datastore Email
    val dataStore = StoreStudentInfo(context)

    val savedUsernameState = dataStore.getUsername.collectAsState(initial = "")
    val savedEmailState = dataStore.getEmail.collectAsState(initial = "")
    val savedIDState = dataStore.getID.collectAsState(initial = "")

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                singleLine = true,
                label = {
                    Text("Username")
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                label = {
                    Text("Email")
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                singleLine = true,
                label = {
                    Text("ID")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 130.dp)) {
            Button(onClick = {
                scope.launch {
                    username = savedUsernameState.value ?: ""
                    email = savedEmailState.value ?: ""
                    id = savedIDState.value ?: ""
                }
            }) {
                Text(text = "Load")
            }

            Button(onClick = {
                scope.launch {
                    dataStore.saveInfo(username, email, id)
                }
            }, Modifier.padding(horizontal = 16.dp)) {
                Text(text = "Save")
            }
            
            Button(onClick = {
                scope.launch {
                    dataStore.clearData()
                    username = ""
                    email = ""
                    id = ""
                }
            }) {
                Text(text = "Clear")
            }
        }

        if (username == "" || id == "") {
            Info(infoName = "<Your Name>", infoID = "<Your College ID>")
        }
        else {
            Info(infoName = username, infoID = id)
        }
    }
}

@Composable
fun Info(
    infoName: String,
    infoID: String
) {
    Column(modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth()) {
        Text(infoName, style = TextStyle(color = Color.Blue))
        Text(infoID, style = TextStyle(color = Color.Blue))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    Assignment1Theme {
        MainScreen()
    }
}