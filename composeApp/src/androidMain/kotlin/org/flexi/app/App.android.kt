package org.flexi.app

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.flexi.app.db.MyDatabase
import org.flexi.app.di.appModule
import org.flexi.app.domain.model.version.Platform
import org.flexi.app.presentation.ui.screens.payment.model.Order
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.io.File

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@AndroidApp)
            androidLogger()
            modules(appModule)
        }
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }
    }
}

internal actual fun openUrl(url: String?) {
    val uri = url?.let { Uri.parse(it) } ?: return
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = uri
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    AndroidApp.INSTANCE.startActivity(intent)
}

actual fun getPlatform(): Platform {
    return Platform.Android
}

actual class DriverFactory actual constructor() {
    private var context: Context = AndroidApp.INSTANCE.applicationContext
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MyDatabase.Schema, context, "MyDatabase.db")
    }
}

actual fun generateInvoicePdf(order: Order): ByteArray {
    val pdfDocument = android.graphics.pdf.PdfDocument()
    val pageInfo =
        android.graphics.pdf.PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val paint = android.graphics.Paint()
    paint.color = android.graphics.Color.BLACK
    paint.textSize = 12f

    canvas.drawText("Invoice", 100f, 50f, paint)
    canvas.drawText("Order #${order.id}", 100f, 70f, paint)

    canvas.drawText("Customer ID: #${order.userId}", 100f, 100f, paint)

    canvas.drawText("Order Date:", 100f, 130f, paint)
    canvas.drawText(order.orderDate, 200f, 130f, paint)
    canvas.drawText("Delivery Date:", 100f, 150f, paint)
    canvas.drawText(order.deliveryDate, 200f, 150f, paint)

    canvas.drawText("Product", 100f, 180f, paint)
    canvas.drawText("Quantity", 250f, 180f, paint)
    canvas.drawText("Price", 350f, 180f, paint)
    var yPosition = 200f
    canvas.drawText(order.productIds.toString(), 100f, yPosition, paint)
    canvas.drawText(order.totalQuantity.toString(), 250f, yPosition, paint)
    canvas.drawText("${order.totalPrice}", 350f, yPosition, paint)
    yPosition += 20f

    canvas.drawText("Total:", 350f, yPosition + 20f, paint)
    canvas.drawText(order.totalPrice.toString(), 400f, yPosition + 20f, paint)

    pdfDocument.finishPage(page)

    val outputStream = java.io.ByteArrayOutputStream()
    pdfDocument.writeTo(outputStream)
    pdfDocument.close()

    return outputStream.toByteArray()
}

actual fun saveInvoiceToFile(data: ByteArray, fileName: String) {
    val context = AndroidApp.INSTANCE.applicationContext
    val file = File(context.getExternalFilesDir(null), fileName)
    file.writeBytes(data)
    val uri = androidx.core.content.FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}