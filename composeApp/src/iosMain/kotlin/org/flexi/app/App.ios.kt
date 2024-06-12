package org.flexi.app

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.flexi.app.db.MyDatabase
import org.flexi.app.domain.model.version.Platform
import org.flexi.app.presentation.ui.screens.payment.model.Order
import platform.Foundation.*
import platform.UIKit.*
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

internal actual fun openUrl(url: String?) {
    val nsUrl = url?.let { NSURL.URLWithString(it) } ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}

actual fun getPlatform(): Platform {
    return Platform.IOS
}

actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MyDatabase.Schema,"YouTubeDatabase.db")
    }
}
actual fun generateInvoicePdf(order: Order): ByteArray {
    // Implement PDF generation using Swift
    // Call the Swift function from Kotlin
}

actual fun saveInvoiceToFile(data: ByteArray, fileName: String) {
    val file = createFileInDocumentsDirectory(fileName)
    file.writeBytes(data)
    val documentInteractionController = UIDocumentInteractionController.interactionControllerWithURL(NSURL.fileURLWithPath(file.absolutePath))
    documentInteractionController.delegate = object : NSObject(), UIDocumentInteractionControllerDelegateProtocol {}
    documentInteractionController.presentOptionsMenuFromRect(CGRectZero, UIWindow.keyWindow, true)
}

// Swift function to create a file in the Documents directory
fun createFileInDocumentsDirectory(fileName: String): File {
    val fileManager = NSFileManager.defaultManager
    val documentsURL = fileManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask).lastObject as NSURL
    val fileURL = documentsURL.URLByAppendingPathComponent(fileName)
    return File(fileURL.path)
}