package com.example.videri.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.videri.ui.icons.LineIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSideSheet(
    onLogout: () -> Unit,
    onCloseDrawer: () -> Unit,
    userName: String = "John Doe",
    userEmail: String = "john.doe@example.com"
) {
    ModalDrawerSheet(
        modifier = Modifier.fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Profile Header
            ProfileHeader(
                userName = userName,
                userEmail = userEmail
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = MaterialTheme.colorScheme.outline
            )

            // Settings Section
            SettingsSection()

            Spacer(modifier = Modifier.weight(1f))

            // Logout Button
            LogoutButton(
                onLogout = onLogout,
                onCloseDrawer = onCloseDrawer
            )
        }
    }
}

@Composable
private fun ProfileHeader(
    userName: String,
    userEmail: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = LineIcons.User,
            contentDescription = "Profile",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = userEmail,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SettingsSection() {
    Text(
        text = "Settings",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp)
    )

    SettingsItem(
        title = "Notifications",
        subtitle = "Manage your notification preferences",
        onClick = { /* TODO: Handle notifications */ }
    )

    SettingsItem(
        title = "Privacy",
        subtitle = "Control your privacy settings",
        onClick = { /* TODO: Handle privacy */ }
    )

    SettingsItem(
        title = "Theme",
        subtitle = "Dark mode, light mode, and more",
        onClick = { /* TODO: Handle theme */ }
    )

    SettingsItem(
        title = "About",
        subtitle = "Version info and credits",
        onClick = { /* TODO: Handle about */ }
    )
}

@Composable
private fun SettingsItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LogoutButton(
    onLogout: () -> Unit,
    onCloseDrawer: () -> Unit
) {
    OutlinedButton(
        onClick = {
            onLogout()
            onCloseDrawer()
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.error
        )
    ) {
        Text("Logout")
    }
}
