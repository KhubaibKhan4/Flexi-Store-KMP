package org.flexi.app.presentation.ui.navigation.sidebar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.flexi.app.presentation.ui.navigation.rails.items.NavigationItem
import org.flexi.app.theme.LocalThemeIsDark

@Composable
fun SidebarMenu(
    items: List<NavigationItem>,
    selectedItemIndex: Int,
    onMenuItemClick: (Int) -> Unit,
    initialExpandedState: Boolean = true,
) {
    val isDark by LocalThemeIsDark.current
    var isExpanded by remember { mutableStateOf(initialExpandedState) }
    val expandIcon = if (isExpanded) Icons.Default.ArrowBack else Icons.Default.ArrowForward
    val sidebarWidth by animateDpAsState(targetValue = if (isExpanded) 200.dp else 75.dp)

    Column(
        modifier = Modifier
            .width(sidebarWidth)
            .fillMaxHeight()
            .background(if (isDark) MaterialTheme.colorScheme.surface else Color(0xFFf1f4f9))
            .padding(16.dp)
    ) {
        if (isExpanded) {
            Text(
                text = "Flexi-Store",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF007BFF),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        IconButton(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier
                .pointerHoverIcon(PointerIcon.Hand)
                .animateContentSize(
                    tween(
                        durationMillis = 300,
                        delayMillis = 400,
                        easing = FastOutLinearInEasing
                    )
                )
                .align(Alignment.End)
        ) {
            Icon(expandIcon, contentDescription = "Expand/Collapse Sidebar")
        }

        Column {
            items.forEachIndexed { index, item ->
                MenuItem(
                    name = item.title,
                    icon = item.selectedIcon,
                    selected = selectedItemIndex == index,
                    onMenuItemClick = { onMenuItemClick(index) },
                    expanded = isExpanded
                )
            }
        }
    }
}

@Composable
fun MenuItem(
    name: String,
    icon: ImageVector,
    selected: Boolean,
    onMenuItemClick: () -> Unit,
    expanded: Boolean
) {
    val isDark by LocalThemeIsDark.current
    val backgroundColor = if (selected) Color(0xFF007BFF) else Color.Transparent
    val contentColor = if (selected) Color.White else if (isDark) White else Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onMenuItemClick)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = if (expanded) 16.dp else 4.dp, vertical =if (expanded) 8.dp else 4.dp)
            .height(48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = name, tint = contentColor)
        if (expanded) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = name, color = contentColor, style = MaterialTheme.typography.bodyLarge)
        }
    }
}