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
@objc class PDFGenerator: NSObject {
    @objc static func generateInvoice(orderId: Int, userId: Int, orderDate: String, deliveryDate: String, productIds: [Int], totalQuantity: Int, totalPrice: Double) -> Data {
        let pdfData = NSMutableData()
        UIGraphicsBeginPDFContextToData(pdfData, CGRect.zero, nil)
        UIGraphicsBeginPDFPage()
        let context = UIGraphicsGetCurrentContext()
        context?.setFillColor(UIColor.black.cgColor)

        let text = """
        Invoice
        Order #\(orderId)
        Customer ID: #\(userId)
        Order Date: \(orderDate)
        Delivery Date: \(deliveryDate)
        Product: \(productIds)
        Quantity: \(totalQuantity)
        Price: \(totalPrice)
        Total: \(totalPrice)
        """

        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.alignment = .left
                let attributes = [
            NSAttributedString.Key.font: UIFont.systemFont(ofSize: 12),
        NSAttributedString.Key.paragraphStyle: paragraphStyle
        ]

        text.draw(in: CGRect(x: 100, y: 50, width: 400, height: 700), withAttributes: attributes)

        UIGraphicsEndPDFContext()
        return pdfData as Data
    }
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
    val data = PDFGenerator.generateInvoice(
        orderId = order.id,
        userId = order.userId,
        orderDate = order.orderDate,
        deliveryDate = order.deliveryDate,
        productIds = order.productIds.toIntArray(),
        totalQuantity = order.totalQuantity,
        totalPrice = order.totalPrice
    )
    return data.toByteArray()
}

actual fun saveInvoiceToFile(data: ByteArray, fileName: String) {
    val fileManager = NSFileManager.defaultManager
    val documentsURL = fileManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask).lastObject as NSURL
    val fileURL = documentsURL.URLByAppendingPathComponent(fileName)
    data.usePinned {
        NSData.dataWithBytes(it.addressOf(0), data.size.toULong()).writeToURL(fileURL, true)
    }

    val documentInteractionController = UIDocumentInteractionController.interactionControllerWithURL(fileURL)
    documentInteractionController.delegate = object : NSObject(), UIDocumentInteractionControllerDelegateProtocol {}
    documentInteractionController.presentOptionsMenuFromRect(CGRectZero, UIWindow.keyWindow, true)
}