package com.application.listcomposeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.application.listcomposeapplication.ui.theme.ListComposeApplicationTheme
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.isSystemBarsVisible = true
            val viewModel = ViewModelProvider(this)[ViewModelClass::class.java]
            val users = setUsers()
            ListComposeApplicationTheme {
                WhichScreenToShow(viewModel = viewModel, users, systemUiController)
            }
        }
    }

    private fun setUsers(): ArrayList<Users> {
        val users = ArrayList<Users>()
        val names = arrayOf(
            "Wahaj Sajid",
            "Mohammad Haroon",
            "Anas ",
            "Mohammad Ahmad",
            "Ali Raza",
            "Noman Raza",
            "Basit Ali",
            "Habibullah",
            "Samiullah",
            "Abdullah"
        )
        for (name in names) {
            val data = Users(name)
            users.add(data)
        }
        return users
    }
}

//@Preview(showSystemUi = true)
//@Composable
//private fun InitialScreenPreview() {
//    InitialScreen(viewModel = ViewModelClass(), statusBar = SystemUiController)
//}

@Preview(widthDp = 420, showBackground = true)
@Composable
fun ListScreenPreview() {
    UserCard()
}


@Composable
fun WhichScreenToShow(
    viewModel: ViewModelClass,
    users: ArrayList<Users>,
    statusBar: SystemUiController
) {
    if (viewModel.showListScreen.value) UsersScreen(users, statusBar)
    else InitialScreen(viewModel = viewModel, statusBar)
}

@Composable
fun InitialScreen(viewModel: ViewModelClass, statusBar: SystemUiController) {
    statusBar.setSystemBarsColor(MaterialTheme.colorScheme.tertiaryContainer)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding()
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Code works Docs", style = MaterialTheme.typography.headlineSmall)
        ElevatedButton(
            onClick = { viewModel.showListScreen.value = !viewModel.showListScreen.value },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Text(text = "Continue")
            Icon(
                painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                contentDescription = "navigate next"
            )
        }
    }
}

@Composable
fun UserCard(name: String = "Wahaj Sajid") {
    var clicked by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
        modifier = Modifier
            .defaultMinSize(minHeight = 105.dp)
            .padding(
                top = WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding(),
                start = 15.dp,
                end = 15.dp,
                bottom = 15.dp
            )
            .clickable { clicked = !clicked }
            .fillMaxWidth(),

        ) {
        Row {
            Text(
                text = "Hello,", style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 10.dp, top = 6.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { clicked = !clicked },

                ) {
                Icon(
                    painter = if (clicked) painterResource(id = R.drawable.baseline_keyboard_arrow_up_24)
                    else painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                    contentDescription = "icon"
                )
            }
        }
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),

                    )
        ) {
            Text(
                text = name,
                modifier = Modifier.padding(start = 5.dp),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            if (clicked) {
                Text(
                    text = "I am a software engineer living in Pakistan currently I am very passionate about new technologies. My current tech stack is Native Android App development which includes (Kotlin, XML, Jetpack Compose, Networking libraries including OkHttp and Retrofit, Firebase , SQLite and Room for local storage.",
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    }
}

data class Users(var name: String)

@Composable
fun UsersScreen(users: ArrayList<Users>, statusBar: SystemUiController) {
    statusBar.setSystemBarsColor(MaterialTheme.colorScheme.tertiaryContainer)
    LazyColumn {
        items(users) { user ->
            UserCard(name = user.name)
        }
    }
}



