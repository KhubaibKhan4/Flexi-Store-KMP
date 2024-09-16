package org.flexi.app

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.usePinned
import org.flexi.app.domain.model.version.Platform
import org.flexi.app.presentation.ui.screens.payment.model.Order
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSMutableData
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.writeToFile
import platform.UIKit.NSFontAttributeName
import platform.UIKit.NSMutableParagraphStyle
import platform.UIKit.NSParagraphStyleAttributeName
import platform.UIKit.NSTextAlignmentLeft
import platform.UIKit.UIApplication
import platform.UIKit.UIFont
import platform.UIKit.UIGraphicsBeginPDFContextToData
import platform.UIKit.UIGraphicsBeginPDFPageWithInfo
import platform.UIKit.UIGraphicsEndPDFContext
import platform.UIKit.UIGraphicsGetCurrentContext
import platform.posix.memcpy

internal actual fun openUrl(url: String?) {
    val nsUrl = url?.let { NSURL.URLWithString(it) } ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}


actual fun getPlatform(): Platform {
    return Platform.IOS
}
@OptIn(ExperimentalForeignApi::class)
actual fun generateInvoicePdf(order: Order): ByteArray {
    val pdfData = NSMutableData()
    val pageSize = CGRectMake(0.0, 0.0, 612.0, 792.0)

    UIGraphicsBeginPDFContextToData(pdfData, pageSize, null)
    UIGraphicsBeginPDFPageWithInfo(pageSize, null)

    val context = UIGraphicsGetCurrentContext()

    val text = """
        Invoice
        Order #${order.id}
        Customer ID: #${order.userId}
        Order Date: ${order.orderDate}
        Delivery Date: ${order.deliveryDate}
        Products: ${order.productIds}
        Quantity: ${order.totalQuantity}
        Total Price: ${order.totalPrice}
    """.trimIndent()

    val paragraphStyle = NSMutableParagraphStyle().apply { NSTextAlignmentLeft }
    val attributes: Map<Any?, Any?> = mapOf(
        NSFontAttributeName to UIFont.systemFontOfSize(12.0),
        NSParagraphStyleAttributeName to paragraphStyle
    )
    UIGraphicsEndPDFContext()

    return pdfData.bytes.let {
        it?.reinterpret<ByteVar>()!!.readBytes(pdfData.length.toInt())
    }
}
@OptIn(ExperimentalForeignApi::class)
actual fun saveInvoiceToFile(data: ByteArray, fileName: String) {
    val fileManager = NSFileManager.defaultManager
    val documentsDirectory = fileManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask).firstOrNull()

    val documentPath = documentsDirectory

    val nsData = data.usePinned { pinnedData ->
        NSData.create(bytes = pinnedData.addressOf(0), length = data.size.toULong())
    }

     try {
        nsData.writeToFile(documentPath.toString(), true)
        documentPath.toString()
    } catch (e: Exception){
        "Failed to save PDF: ${e.message}"
    }
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    return this.bytes?.let { bytesPointer ->
        ByteArray(this.length.toInt()).apply {
            usePinned { pinned ->
                memcpy(pinned.addressOf(0), bytesPointer, this@toByteArray.length)
            }
        }
    } ?: ByteArray(0)
}