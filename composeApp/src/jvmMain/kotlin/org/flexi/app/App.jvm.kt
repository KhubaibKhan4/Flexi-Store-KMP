package org.flexi.app

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.flexi.app.domain.model.version.Platform
import org.flexi.app.presentation.ui.screens.payment.model.Order
import java.awt.Desktop
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI

internal actual fun openUrl(url: String?) {
    val uri = url?.let { URI.create(it) } ?: return
    Desktop.getDesktop().browse(uri)
}

actual fun getPlatform(): Platform {
    return Platform.Desktop
}

actual fun generateInvoicePdf(order: Order): ByteArray {
    val document = PDDocument()
    val page = PDPage()
    document.addPage(page)

    val contentStream = PDPageContentStream(document, page)
    contentStream.beginText()
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12f)
    contentStream.newLineAtOffset(100f, 750f)
    contentStream.showText("Invoice")
    contentStream.newLineAtOffset(0f, -20f)
    contentStream.showText("Order #${order.id}")
    contentStream.newLineAtOffset(0f, -20f)
    contentStream.showText("Customer ID: #${order.userId}")
    contentStream.newLineAtOffset(0f, -20f)
    contentStream.showText("Order Date: ${order.orderDate}")
    contentStream.newLineAtOffset(0f, -20f)
    contentStream.showText("Delivery Date: ${order.deliveryDate}")
    contentStream.newLineAtOffset(0f, -20f)
    contentStream.showText("Product: ${order.productIds}")
    contentStream.newLineAtOffset(0f, -20f)
    contentStream.showText("Quantity: ${order.totalQuantity}")
    contentStream.newLineAtOffset(0f, -20f)
    contentStream.showText("Price: ${order.totalPrice}")
    contentStream.newLineAtOffset(0f, -20f)
    contentStream.showText("Total: ${order.totalPrice}")
    contentStream.endText()
    contentStream.close()

    val outputStream = ByteArrayOutputStream()
    document.save(outputStream)
    document.close()

    return outputStream.toByteArray()
}

actual fun saveInvoiceToFile(data: ByteArray, fileName: String) {
    val file = File(System.getProperty("user.home"), fileName)
    file.writeBytes(data)
    java.awt.Desktop.getDesktop().open(file)
}