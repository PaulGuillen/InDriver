package com.devpaul.indriver.presentation.screens.profile.update.component

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devpaul.indriver.R
import com.devpaul.indriver.presentation.components.DefaultIconButton
import com.devpaul.indriver.presentation.components.DefaultTextField
import com.devpaul.indriver.presentation.screens.profile.update.ProfileUpdateViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun ProfileUpdateContent(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    vm: ProfileUpdateViewModel = hiltViewModel(),
) {

    val state = vm.state

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
                title = "Actualizar Usuario",
                icon = Icons.Default.Edit,
                onClick = {

                }
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
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
                    if (!vm.user?.image.isNullOrBlank()) {
                        AsyncImage(
                            model = vm.user?.image,
                            contentDescription = "Image",
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.user_image),
                            contentDescription = "Person",
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                DefaultTextField(
                    modifier = Modifier,
                    value = state.name,
                    label = "Nombre",
                    icon = Icons.Default.Person,
                    onValueChange = {
                        vm.onNameChange(it)
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                DefaultTextField(
                    modifier = Modifier,
                    value = state.lastname,
                    label = "Apellido",
                    icon = Icons.Outlined.Person,
                    onValueChange = {
                        vm.onLastnameChange(it)
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                DefaultTextField(
                    modifier = Modifier,
                    value = state.phone ,
                    label = "Celular",
                    icon = Icons.Default.Phone,
                    onValueChange = {
                        vm.onPhoneChange(it)
                    },
                )
            }
        }
    }
}