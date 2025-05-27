package com.example.jitbook.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.jitbook.R
import com.example.jitbook.book.app.Route
import com.example.jitbook.book.theme.AuthState
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.viewmodel.AppThemeViewModel
import com.example.jitbook.book.theme.viewmodel.AuthViewModel
import kotlin.io.path.Path

@Composable
fun ProfileScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    appThemeViewModel: AppThemeViewModel,
    modifier: Modifier = Modifier
) {
    val currentUser by authViewModel.currentUser.observeAsState()
    val user = currentUser

    var isEditing by remember { mutableStateOf(false) }

    val avatarPainter = if (user?.photoUrl != null) {
        rememberAsyncImagePainter(model = user.photoUrl)
    } else {
        painterResource(id = R.drawable.logo)
    }
    val snackbarHostState = remember { SnackbarHostState() }

    val authState by authViewModel.authState.observeAsState()
    val isDarkMode by appThemeViewModel.isDarkMode

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            snackbarHostState.showSnackbar((authState as AuthState.Success).message)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .align(Alignment.TopCenter)
        ) {
            val width = size.width
            val height = size.height

            drawPath(
                path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(0f, 0f)
                    lineTo(width, 0f)
                    lineTo(width, height * 0.75f)
                    cubicTo(
                        width * 0.75f, height,
                        width * 0.25f, height * 0.75f,
                        0f, height
                    )
                    close()
                },
                color = Color(0xFFFFCC80)
            )
        }

        FallingDots(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f) // nằm sau nội dung
        )

        Column(
            modifier = Modifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(120.dp) // lớn hơn một chút
                        .zIndex(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .border(5.dp, Color.White, CircleShape)
                    ) {
                        Image(
                            painter = avatarPainter,
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    // Nút edit góc dưới
                    IconButton(
                        onClick = { isEditing = true },
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.BottomEnd)
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = currentUser?.displayName ?: "Ronald Richards",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                )

                Text(
                    text = currentUser?.email ?: "Email",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            ProfileMenuItem(
                icon = R.drawable.ic_dark_mode,
                iconBg = MaterialTheme.colorScheme.background,
                iconTint = Color(0xFF226DB4),
                title = "Dark Mode",
                trailing = {
                    Switch(checked = isDarkMode,
                        onCheckedChange = {
                            appThemeViewModel.setDarkMode(it)
                        })
                },
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        authViewModel.logout()
                        navController.navigate(Route.BookWelcome.route) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Logout",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
        if (isEditing) {
            EditProfileDialog(
                currentName = currentUser?.displayName ?: "",
                currentPhoto = currentUser?.photoUrl?.toString() ?: "",
                onDismiss = { isEditing = false },
                onSave = { name, photo ->
                    authViewModel.updateProfile(name, photo)
                    isEditing = false
                }
            )
        }
    }

}

@Composable
fun EditProfileDialog(
    currentName: String,
    currentPhoto: String,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var selectedPhotoUri by remember { mutableStateOf<Uri?>(null) }

    // Dùng launcher để chọn ảnh từ thư viện
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedPhotoUri = uri
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Chỉnh sửa thông tin") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên hiển thị") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Image, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Chọn ảnh từ thư viện")
                }
                Spacer(modifier = Modifier.height(12.dp))

                val previewUri = selectedPhotoUri ?: currentPhoto.takeIf { it.isNotBlank() }
                    ?.let { Uri.parse(it) }
                previewUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Ảnh đại diện",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val photoUrl = selectedPhotoUri?.toString() ?: currentPhoto
                onSave(name, photoUrl)
            }) {
                Text("Lưu")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}


@Composable
fun ProfileMenuItem(
    icon: Int,
    iconBg: Color,
    iconTint: Color,
    title: String,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(iconBg, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.weight(1f)
        )
        trailing?.invoke() ?: Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    JITBookTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {

        ProfileScreen(
            navController = NavHostController(LocalContext.current),
            authViewModel = AuthViewModel(),
            appThemeViewModel = AppThemeViewModel()
        )

    }
}