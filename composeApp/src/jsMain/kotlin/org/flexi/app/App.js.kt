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
    val doc = jsPDF()
    doc.text("Invoice for Order #${order.id}", 10, 10)

    doc.save("invoice_${order.id}.pdf")
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