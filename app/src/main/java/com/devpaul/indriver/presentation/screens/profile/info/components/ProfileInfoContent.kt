package com.devpaul.indriver.presentation.screens.profile.info.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.indriver.R
import com.devpaul.indriver.presentation.components.DefaultIconButton

@Composable
fun ProfileInfoContent(navHostController: NavHostController, paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF009688), Color(0xFF002C29))
                        ),
                    ),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "Perfil de Usuario",
                    modifier = Modifier.padding(top = 40.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            DefaultIconButton(
                modifier = Modifier.fillMaxWidth(),
                title = "Editar Perfil",
                icon = Icons.Default.Edit
            )
            Spacer(modifier = Modifier.height(20.dp))
            DefaultIconButton(
                modifier = Modifier.fillMaxWidth(),
                title = "Cerrar Sesión",
                icon = Icons.AutoMirrored.Filled.ExitToApp
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(460.dp)
                .padding(horizontal = 36.dp, vertical = 100.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Box(
                    modifier = Modifier
                        .size(118.dp)
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user_image),
                        contentDescription = "Person",
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Nombre de Usuario",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Correo de Usuario",
                    fontSize = 18.sp,
                )
                Text(
                    text = "Teléfono de Usuario",
                    fontSize = 18.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileInfoContentPreview() {
    ProfileInfoContent(navHostController = rememberNavController(), paddingValues = PaddingValues())
}
