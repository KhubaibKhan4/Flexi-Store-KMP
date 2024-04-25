package org.flexi.app.utils

fun extractItemId(response: String): Int? {
    val regex = Regex("""(\d+)""")
    val matchResult = regex.find(response)
    return matchResult?.value?.toIntOrNull()
}