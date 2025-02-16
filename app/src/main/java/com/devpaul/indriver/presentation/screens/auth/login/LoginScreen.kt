package com.devpaul.indriver.presentation.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpaul.indriver.R

@Composable
fun LoginScreen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF5F961C), Color(0xFF8BC34A))
                    )
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Iniciar Sesion",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .rotate(90f)
                        .padding(top = 116.dp)
                )
                Spacer(modifier = Modifier.height(140.dp))
                Text(
                    text = "Registrarse",
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .rotate(90f)
                        .padding(top = 64.dp)
                )
                Spacer(modifier = Modifier.height(100.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 60.dp, bottom = 36.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF66B400), Color(0xFFB5D5A4))
                        ),
                        shape = RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.statusBarsPadding()
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Bienvenido",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "de nuevo",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Image(
                        modifier = Modifier
                            .size(150.dp)
                            .padding(16.dp),
                        painter = painterResource(id = R.drawable.car_white),
                        contentDescription = "",
                    )
                    Text(
                        text = "Log in",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") }
                    )

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") }
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Iniciar Sesion")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Spacer(
                            modifier = Modifier
                                .width(25.dp)
                                .height(1.dp)
                                .background(Color.White)
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "O",
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                        Spacer(
                            modifier = Modifier
                                .width(25.dp)
                                .height(1.dp)
                                .background(Color.White)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = "No tienes cuenta?",
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                        Text(
                            text = "Registrate",
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    }
}