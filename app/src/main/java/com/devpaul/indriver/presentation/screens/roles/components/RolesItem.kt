package com.devpaul.indriver.presentation.screens.roles.components

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devpaul.indriver.R
import com.devpaul.indriver.domain.model.res.Role
import com.devpaul.indriver.presentation.navigation.Graph

@Composable
fun RolesItem(role: Role, navHostController: NavHostController) {

    Column(
        modifier = Modifier
            .clickable {
                if (role.id == "CLIENT") {
                    navHostController.navigate(route = Graph.CLIENT) {
                        popUpTo(Graph.AUTH) {
                            inclusive = true
                        }
                    }
                } else if (role.id == "DRIVER") {
                    navHostController.navigate(route = Graph.DRIVER) {
                        popUpTo(Graph.AUTH) {
                            inclusive = true
                        }
                    }
                }

            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        AsyncImage(
            modifier = Modifier
                .size(150.dp),
            model = role.image,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.user_image)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = role.name,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            textAlign = TextAlign.Center
        )
    }

}