package org.flexi.app

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import io.kamel.core.utils.URL
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.datetime.internal.JsNonModule
import org.flexi.app.db.MyDatabase
import org.flexi.app.domain.model.version.Platform
import org.flexi.app.presentation.ui.screens.payment.model.Order
import org.w3c.dom.HTMLAnchorElement
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag

internal actual fun openUrl(url: String?) {
    url?.let { window.open(it) }
}

actual fun getPlatform(): Platform {
    return Platform.Web
}

actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        val workerScriptUrl = js("import.meta.url.replace('kotlin', 'node_modules/@cashapp/sqldelight-sqljs-worker/sqljs.worker.js')")
        val driver = WebWorkerDriver(workerScriptUrl).also { MyDatabase.Schema.create(it) }
        return driver
    }
}
external class jsPDF {
    fun text(text: String, x: Int, y: Int)
    fun save(filename: String)
}
actual fun generateInvoicePdf(order: Order): ByteArray {
    val invoiceHtml = """
        <html>
        <head>
            <style>
                body { font-family: Arial, sans-serif; }
                h1 { text-align: center; }
                .invoice-details { margin-bottom: 20px; }
                .invoice-details div { margin: 5px 0; }
                .invoice-table { width: 100%; border-collapse: collapse; margin-top: 20px; }
                .invoice-table th, .invoice-table td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                .invoice-table th { background-color: #f2f2f2; }
            </style>
        </head>
        <body>
            <h1>Invoice</h1>
            <div class="invoice-details">
                <div>Order #: ${order.id}</div>
                <div>Customer ID: ${order.userId}</div>
                <div>Order Date: ${order.orderDate}</div>
                <div>Delivery Date: ${order.deliveryDate}</div>
            </div>
            <table class="invoice-table">
                <tr>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
                <tr>
                    <td>${order.productIds}</td>
                    <td>${order.totalQuantity}</td>
                    <td>${order.totalPrice}</td>
                </tr>
                <tr>
                    <td colspan="2">Total</td>
                    <td>${order.totalPrice}</td>
                </tr>
            </table>
        </body>
        </html>
    """.trimIndent()

    val blob = Blob(arrayOf(invoiceHtml), BlobPropertyBag("text/html"))
    val url = URL.createObjectURL(blob)

    val a = document.createElement("a") as HTMLAnchorElement
    a.href = url
    a.download = "invoice_${order.id}.html"
    document.body?.appendChild(a)
    a.click()
    document.body?.removeChild(a)

    return ByteArray(0)
}

actual fun saveInvoiceToFile(data: ByteArray, fileName: String) {
    val blob = Blob(arrayOf(data), BlobPropertyBag("application/pdf"))
    val url = URL.createObjectURL(blob)
    val a = document.createElement("a") as HTMLAnchorElement
    a.href = url
    a.download = fileName
    document.body?.appendChild(a)
    a.click()
    document.body?.removeChild(a)
}