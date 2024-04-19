package org.flexi.app.presentation.ui.screens.detail.all

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.flexi.app.domain.model.books.BooksItem
import org.flexi.app.domain.model.products.Products
import org.flexi.app.presentation.ui.components.BooksItems
import org.flexi.app.presentation.ui.components.ElectronicsItems
import org.flexi.app.presentation.ui.components.FeaturedItems
import org.flexi.app.presentation.ui.components.FoodItems
import org.flexi.app.presentation.ui.components.FurnitureItems

@OptIn(ExperimentalMaterial3Api::class)
class SeeAllProducts(
    val products: List<Products>?,
    val books: List<BooksItem>?,
    val category: String,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth()
                        .offset(y = (-12).dp),
                    title = { Text(text = category) },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigator?.pop()
                        }) {
                            Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = 34.dp,
                        start = 16.dp, end = 16.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                println(books)
                if (books?.isNotEmpty()==true){
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(150.dp),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        books?.let { bookList ->
                            items(bookList) { book ->
                                println(book)
                                BooksItems(book)
                            }
                        }
                    }
                }else{
                    products?.let { it1 ->
                        CategorySpecificProducts(
                            products = it1,
                            books = null,
                            category = category
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun CategorySpecificProducts(products: List<Products>, books: List<BooksItem>?, category: String) {
    when (category) {
        "Featured" -> {
            FeaturedProducts(products)
        }

        "Food & Groceries" -> {
            FoodAndGroceriesProducts(products)
        }

        "Furniture & Decor" -> {
            FurnitureAndDecorProducts(products)
        }

        "Electronics Accessories" -> {
            ElectronicsAccessoriesProducts(products)
        }

        "Books & Journals" -> {
            BooksAndJournalsProducts(books)
        }

        else -> {
            Text("Category not recognized")
        }
    }
}

@Composable
fun FeaturedProducts(products: List<Products>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(products) { product ->
            FeaturedItems(product)
        }
    }
}

@Composable
fun FoodAndGroceriesProducts(products: List<Products>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(products) { product ->
            FoodItems(product)
        }
    }
}

@Composable
fun FurnitureAndDecorProducts(products: List<Products>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products) { product ->
            FurnitureItems(product)
        }
    }
}


@Composable
fun ElectronicsAccessoriesProducts(products: List<Products>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products) { product ->
            ElectronicsItems(product)
        }
    }
}

@Composable
fun BooksAndJournalsProducts(books: List<BooksItem>?) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        books?.let { bookList ->
            items(bookList) { book ->
                println(book)
                //BooksItems(book)
                Text(book.title, fontSize = 44.sp)
            }
        }
    }
}