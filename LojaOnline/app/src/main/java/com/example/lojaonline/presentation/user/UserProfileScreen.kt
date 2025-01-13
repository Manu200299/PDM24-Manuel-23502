import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.domain.model.User
import com.example.lojaonline.presentation.ProfileState
import com.example.lojaonline.presentation.UserProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    sessionManager: SessionManager,
    onLogout: () -> Unit,
    onAddAddressClick: () -> Unit,
    onAddProductsClick: () -> Unit,
    onProductsClick: () -> Unit,
    onViewOrdersClick: () -> Unit,
) {
    val viewModel: UserProfileViewModel = viewModel(
        factory = UserProfileViewModel.Factory(sessionManager)
    )
    val profileState by viewModel.profileState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                actions = {
                    IconButton(onClick = {
                        viewModel.logout()
                        onLogout()
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val state = profileState) {
            is ProfileState.Idle -> ProfileLoadingPlaceholder()
            is ProfileState.Loading -> ProfileLoadingPlaceholder()
            is ProfileState.Success -> ProfileContent(
                user = state.user,
                onAddAddressClick = onAddAddressClick,
                onAddProductsClick = onAddProductsClick,
                onProductsClick = onProductsClick,
                onViewOrdersClick = onViewOrdersClick,
                modifier = Modifier.padding(innerPadding)
            )
            is ProfileState.Error -> ProfileErrorContent(
                error = state.message,
                onRetry = { viewModel.loadUserProfile() },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ProfileLoadingPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ProfileContent(
    user: User,
    onAddAddressClick: () -> Unit,
    onAddProductsClick: () -> Unit,
    onProductsClick: () -> Unit,
    onViewOrdersClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.AccountCircle,
            contentDescription = "User Icon",
            modifier = Modifier
                .size(80.dp)
                .clip(MaterialTheme.shapes.large),
            )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome, ${user.username}!",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = user.email,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))

        ProfileSection(title = "Account Management") {
            ProfileButton(
                text = "Add Address",
                icon = Icons.Default.Home,
                onClick = onAddAddressClick
            )
        }

        ProfileSection(title = "Products") {
            ProfileButton(
                text = "Add Products",
                icon = Icons.Default.Add,
                onClick = onAddProductsClick
            )
            ProfileButton(
                text = "View Products",
                icon = Icons.Default.List,
                onClick = onProductsClick
            )
        }

        ProfileSection(title = "Orders") {
            ProfileButton(
                text = "View All Orders",
                icon = Icons.Default.ShoppingCart,
                onClick = onViewOrdersClick
            )
        }
    }
}

@Composable
fun ProfileSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ProfileButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}

@Composable
fun ProfileErrorContent(
    error: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Close,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Error: $error",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

