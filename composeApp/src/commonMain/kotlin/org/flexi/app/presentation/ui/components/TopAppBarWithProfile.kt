package org.flexi.app.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import flexi_store.composeapp.generated.resources.Res
import flexi_store.composeapp.generated.resources.avatar
import flexi_store.composeapp.generated.resources.ic_cyclone
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopAppBarWithProfile(
    name: String,
    onSearchClicked: () -> Unit,
    onNotificationsClicked: () -> Unit,
    profileImageUrl: String? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (profileImageUrl.isNullOrEmpty()) {
            var profile by remember { mutableStateOf("") }
            profileImageUrl?.let {
                profile = it
            }
            val image: Resource<Painter> = asyncPainterResource(data = profile)
            KamelImage(
                resource = image,
                contentDescription = "Profile",
                modifier = Modifier.size(25.dp)
                    .clip(CircleShape)
            )
        } else {
            LocalAvatar()
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = "Hi, $name",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = Color.Black
            )
            Text(
                text = "Let's go shopping",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.LightGray
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.clickable { onSearchClicked() }
        )
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,
            modifier = Modifier.clickable { onNotificationsClicked() }
        )
    }
}