package org.flexi.app.domain.model.books


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksItem(
    @SerialName("annotations")
    val annotations: String = "",
    @SerialName("author")
    val author: String = "",
    @SerialName("averageRating")
    val averageRating: Double = 0.0,
    @SerialName("awards")
    val awards: String = "",
    @SerialName("binding")
    val binding: String = "",
    @SerialName("category")
    val category: String = "",
    @SerialName("contributors")
    val contributors: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("dimensions")
    val dimensions: String = "",
    @SerialName("edition")
    val edition: String = "",
    @SerialName("format")
    val format: String = "",
    @SerialName("genre")
    val genre: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("imageUrl")
    val imageUrl: String = "",
    @SerialName("isbn")
    val isbn: String = "",
    @SerialName("language")
    val language: String = "",
    @SerialName("pageCount")
    val pageCount: Int = 0,
    @SerialName("price")
    val price: Double = 0.0,
    @SerialName("publicationDate")
    val publicationDate: String = "",
    @SerialName("publicationYear")
    val publicationYear: Int = 0,
    @SerialName("publisher")
    val publisher: String = "",
    @SerialName("stock")
    val stock: Int = 0,
    @SerialName("tableOfContents")
    val tableOfContents: String = "",
    @SerialName("tags")
    val tags: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("weight")
    val weight: Double = 0.0
)