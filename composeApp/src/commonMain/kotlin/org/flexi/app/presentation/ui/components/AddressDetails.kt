package org.flexi.app.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import flexi_store.composeapp.generated.resources.Res
import flexi_store.composeapp.generated.resources.maps
import org.jetbrains.compose.resources.painterResource

@Composable
fun AddressDetails(
    cityName: String,
    postalCode: String,
    address: String,
    onEditClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        elevation =CardDefaults.cardElevation( 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Address",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Edit",
                    fontSize = 16.sp,
                    color = Color(0xFFe85110),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(onClick = onEditClicked)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(Res.drawable.maps),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(140.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(14.dp))

                )
                Spacer(modifier = Modifier.width(4.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = cityName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Postal Code: $postalCode",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = address,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}