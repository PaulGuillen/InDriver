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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpaul.indriver.R
import com.devpaul.indriver.presentation.components.DefaultTextField

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
                        colors = listOf(Color(0xFF25776F), Color(0xFF030303))
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
                    text = "Inicio",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .rotate(90f)
                        .padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.height(140.dp))
                Text(
                    text = "Registro",
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .rotate(90f)
                        .padding(top = 36.dp)
                )
                Spacer(modifier = Modifier.height(100.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 60.dp, bottom = 30.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF009688), Color(0xFF002C29))
                        ),
                        shape = RoundedCornerShape(bottomStart = 30.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(end = 25.dp, start = 25.dp),
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Bienvenido",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "de nuevo...",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, end = 4.dp),
                    ) {
                        Image(
                            modifier = Modifier
                                .size(140.dp)
                                .align(Alignment.CenterEnd),
                            painter = painterResource(id = R.drawable.car_white),
                            contentDescription = "",
                        )
                    }
                    Text(
                        text = "Inicio de sesión",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    DefaultTextField(
                        modifier = Modifier,
                        value = email,
                        label = "Email",
                        icon = Icons.Outlined.Email,
                        onValueChange = { email = it },
                        keyboardType = KeyboardType.Email,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    DefaultTextField(
                        modifier = Modifier,
                        value = password,
                        label = "Password",
                        icon = Icons.Outlined.Lock,
                        onValueChange = { password = it },
                        keyboardType = KeyboardType.Password,
                        hideText = true,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Button(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .width(200.dp)
                                .height(50.dp),
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(Color.Black)
                        ) {
                            Text("Iniciar Sesion", fontSize = 16.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
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
                            fontSize = 20.sp,
                        )
                        Spacer(
                            modifier = Modifier
                                .width(25.dp)
                                .height(1.dp)
                                .background(Color.White)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = "No tienes cuenta?",
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Registrate",
                            color = Color.White,
                            fontSize = 16.sp,
                            style = TextStyle(
                                textDecoration = TextDecoration.Underline
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}