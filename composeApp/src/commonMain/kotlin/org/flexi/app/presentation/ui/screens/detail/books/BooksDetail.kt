package org.flexi.app.presentation.ui.screens.detail.books

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Toc
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.flexi.app.domain.model.books.BooksItem
import org.flexi.app.presentation.ui.screens.detail.ExpandableDescription
import org.flexi.app.utils.Constant.BASE_URL
import org.flexi.app.utils.generateRandomColor

class BooksDetail(
    val booksItem: BooksItem,
) : Screen {
    @Composable
    override fun Content() {
        val scrollState = rememberScrollState()
        val navigator = LocalNavigator.current
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(generateRandomColor())
                .verticalScroll(scrollState),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .padding(top = 200.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            topStart = 32.dp, topEnd = 32.dp
                        )
                    ),

            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(
                                topStart = 32.dp, topEnd = 32.dp
                            )
                        ),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 180.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = booksItem.author,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            color = Color.DarkGray,
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = booksItem.title,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            color = Color.LightGray
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 12.dp, end = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "About Book",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .background(Color(0XFF59bab2))
                                    .clip(RoundedCornerShape(12.dp))
                                    .padding(6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BooksRating(
                                    rating = booksItem.averageRating.toString(),
                                    text = "Rating"
                                )
                                BooksRating(
                                    rating = booksItem.pageCount.toString(),
                                    text = "Pages"
                                )
                                BooksRating(
                                    rating = booksItem.category,
                                    text = "Category"
                                )
                                BooksRating(
                                    rating = booksItem.genre,
                                    text = "Genre"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        ExpandableDescription(booksItem.description)
                        Spacer(modifier = Modifier.height(30.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Business,
                                contentDescription = "Publisher Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = booksItem.publisher,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = Color.Gray,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Event,
                                contentDescription = "Publication Year Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Publication Year: ${booksItem.publicationYear}",
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = Color.Gray,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = "ISBN Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ISBN: ${booksItem.isbn}",
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = Color.Gray,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "Language Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Language: ${booksItem.language}",
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = Color.Gray,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatListNumbered,
                                contentDescription = "Format Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Format: ${booksItem.format}",
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = Color.Gray,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Book,
                                contentDescription = "Edition Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Edition: ${booksItem.edition}",
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = Color.Gray,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Toc,
                                contentDescription = "Binding Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Binding: ${booksItem.binding}",
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = Color.Gray,
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Publication Date Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Publication Date: ${booksItem.publicationDate}",
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                color = Color.Gray,
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        TextButton(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF22877f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .padding(start = 6.dp, end = 6.dp)
                        ) {
                            Text(
                                text = "Buy Now"
                            )
                        }
                        Spacer(modifier = Modifier.height(34.dp))
                    }
                }
            }
            val image: Resource<Painter> = asyncPainterResource(BASE_URL + booksItem.imageUrl)
            KamelImage(
                resource = image,
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopCenter)
                    .padding(top = 75.dp)
                    .width(240.dp)
                    .height(240.dp)
                    .clip(RoundedCornerShape(6.dp))
            )

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 30.dp, end = 12.dp).align(Alignment.TopEnd)
                    .clickable {
                      navigator?.pop()
                    },
                tint = Color.White
            )
        }
    }

}

@Composable
fun BooksRating(
    rating: String,
    text: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = rating,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Text(
            text = text,
            fontSize = MaterialTheme.typography.labelLarge.fontSize,
            fontWeight = FontWeight.Normal,
            color = Color.White
        )
    }
}