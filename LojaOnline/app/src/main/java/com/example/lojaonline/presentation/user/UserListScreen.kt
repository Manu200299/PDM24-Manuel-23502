package com.example.lojaonline.presentation.user

import com.example.lojaonline.presentation.register.RegisterUserViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lojaonline.domain.model.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserListScreen(
    viewModel: UserListViewModel = viewModel(),
    viewModelAdd: RegisterUserViewModel = viewModel()
) {
    val users by viewModel.users.collectAsState()

    val headerCustomColor = Color(0xFF0061b0)

    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(color = headerCustomColor)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "User List",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall)
        }
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            items(users) { user ->
//                UserItem(user = user)
//            }
//        }
        // Button to add a user
        Button(
            onClick = {
                val currentDateTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                val formattedDateTime = currentDateTime.format(formatter)
                val parsedDate = formattedDateTime
//                val newUser = UserAdd(username = "testandroid", email = "testeandroid@foda-se.com", password = "testeandroid", createdAt = formattedDateTime, updatedAt = formattedDateTime)
//                viewModelAdd.registerUser(newUser)
            },
            modifier = Modifier.padding(16.dp)
                .background(color = Color.Black)
        ) {
            Text("Add User")
        }
    }
}

@Composable
fun UserItem(user: User) {
    val boxCustomColor = Color(0xFF0061b0)

    // Crypto list component
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // Whole row
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color = boxCustomColor),
                contentAlignment = Alignment.Center
            ) {
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            )
            {
                // Crypto Name
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                // Crypto Symbol
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}