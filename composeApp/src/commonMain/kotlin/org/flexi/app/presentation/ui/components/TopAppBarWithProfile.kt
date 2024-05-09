package org.flexi.app.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun TopAppBarWithProfile(
    name: String,
    itemCount: Int,
    onCartClicked: () -> Unit,
    onProfileClick: () -> Unit,
    profileImageUrl: String? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        if (profileImageUrl?.isNotEmpty() == true) {
            var profile by remember { mutableStateOf("") }
            profileImageUrl.let {
                profile = it
            }
            val image: Resource<Painter> = asyncPainterResource(data = profile)
            KamelImage(
                resource = image,
                contentDescription = "Profile",
                modifier = Modifier.size(55.dp)
                    .clip(CircleShape)
                    .clickable {
                        onProfileClick()
                    }
            )
        } else {
            LocalAvatar { onProfileClick() }
        }
        Column(
            modifier = Modifier.padding(start = 4.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hi, $name",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Let's go shopping",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.LightGray,
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        BadgedBox(
            modifier = Modifier
                .padding(end = 4.dp)
                .clickable { onCartClicked() },
            badge = {
                if (itemCount != null) {
                    Badge {
                        Text(text = itemCount.toString())
                    }
                } else if (itemCount == null) {
                    Badge()
                }
            }) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = ""
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
    }
}