package org.flexi.app.presentation.ui.screens.setting.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.flexi.app.domain.model.user.User

class AccountScreen(
    private val user: User,
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var editedUser by remember { mutableStateOf(user.copy()) }
        var isEditing by remember { mutableStateOf(false) }
        val navigator = LocalNavigator.current

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Account Setting",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigator?.pop()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More Options",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = it.calculateTopPadding(),
                        bottom = 34.dp
                    )
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                UserDetailsCard(
                    label = "Username",
                    value = if (isEditing) editedUser.username else user.username,
                    icon = Icons.Outlined.Person,
                    isEditing = isEditing,
                    onValueChanged = { editedUser = editedUser.copy(username = it) }
                )
                UserDetailsCard(
                    label = "Full Name",
                    value = if (isEditing) editedUser.fullName else user.fullName,
                    icon = Icons.Outlined.PersonPin,
                    isEditing = isEditing,
                    onValueChanged = { editedUser = editedUser.copy(fullName = it) }
                )
                UserDetailsCard(
                    label = "Email",
                    value = if (isEditing) editedUser.email else user.email,
                    icon = Icons.Outlined.Email,
                    isEditing = isEditing,
                    onValueChanged = { editedUser = editedUser.copy(email = it) }
                )
                UserDetailsCard(
                    label = "Address",
                    value = if (isEditing) editedUser.address else user.address,
                    icon = Icons.Outlined.LocationOn,
                    isEditing = isEditing,
                    onValueChanged = { editedUser = editedUser.copy(address = it) }
                )
                UserDetailsCard(
                    label = "City",
                    value = if (isEditing) editedUser.city else user.city,
                    icon = Icons.Outlined.LocationCity,
                    isEditing = isEditing,
                    onValueChanged = { editedUser = editedUser.copy(city = it) }
                )
                UserDetailsCard(
                    label = "Country",
                    value = if (isEditing) editedUser.country else user.country,
                    icon = Icons.Outlined.LocationCity,
                    isEditing = isEditing,
                    onValueChanged = { editedUser = editedUser.copy(country = it) }
                )
                UserDetailsCard(
                    label = "Postal Code",
                    value = if (isEditing) editedUser.postalCode else user.postalCode,
                    icon = Icons.Outlined.LocationCity,
                    isEditing = isEditing,
                    onValueChanged = { editedUser = editedUser.copy(postalCode = it) }
                )
                UserDetailsCard(
                    label = "Phone Number",
                    value = if (isEditing) editedUser.phoneNumber else user.phoneNumber,
                    icon = Icons.Outlined.Phone,
                    isEditing = isEditing,
                    onValueChanged = { editedUser = editedUser.copy(phoneNumber = it) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { isEditing = true },
                    enabled = !isEditing,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Edit Account")
                }

                if (isEditing) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            // Save changes
                            isEditing = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Save Changes")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsCard(
    label: String,
    value: String,
    icon: ImageVector,
    isEditing: Boolean,
    onValueChanged: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = label, style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(4.dp))
                if (isEditing) {
                    OutlinedTextField(
                        value = value,
                        onValueChange = onValueChanged,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.Gray)
                    )
                } else {
                    Text(text = value, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}