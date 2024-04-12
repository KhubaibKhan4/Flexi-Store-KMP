package org.flexi.app.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import flexi_store.composeapp.generated.resources.Res
import flexi_store.composeapp.generated.resources.avatar
import flexi_store.composeapp.generated.resources.ic_cyclone
import org.jetbrains.compose.resources.painterResource

@Composable
fun LocalAvatar() {
    Image(
        painter = painterResource(Res.drawable.avatar),
        contentDescription = null,
        modifier = Modifier.size(25.dp)
    )
}