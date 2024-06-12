package org.flexi.app

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.flexi.app.db.MyDatabase
import org.flexi.app.domain.model.version.Platform
import org.flexi.app.presentation.ui.screens.payment.model.Order
import java.awt.Desktop
import java.io.File
import java.net.URI

internal actual fun openUrl(url: String?) {
    val uri = url?.let { URI.create(it) } ?: return
    Desktop.getDesktop().browse(uri)
}

actual fun getPlatform(): Platform {
    return Platform.Desktop
}

actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        if (!File("YouTubeDatabase.db").exists()) {
            MyDatabase.Schema.create(driver)
        }
        return driver
    }
}
actual fun generateInvoicePdf(order: Order): ByteArray {
    val document = PDDocument()
    val page = PDPage()
    document.addPage(page)

    val contentStream = PDPageContentStream(document, page)
    contentStream.beginText()
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12f)
    contentStream.newLineAtOffset(100f, 700f)
    contentStream.showText("Invoice for Order #${order.id}")
    // Add more details to the invoice...
    contentStream.endText()
    contentStream.close()

    val outputStream = java.io.ByteArrayOutputStream()
    document.save(outputStream)
    document.close()

    return outputStream.toByteArray()
}

actual fun saveInvoiceToFile(data: ByteArray, fileName: String) {
    val file = File(System.getProperty("user.home"), fileName)
    file.writeBytes(data)
    java.awt.Desktop.getDesktop().open(file)
}