package org.flexi.app

import org.flexi.app.domain.model.version.Platform
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

internal actual fun openUrl(url: String?) {
    val nsUrl = url?.let { NSURL.URLWithString(it) } ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}

actual fun getPlatform(): Platform {
    return Platform.IOS
}