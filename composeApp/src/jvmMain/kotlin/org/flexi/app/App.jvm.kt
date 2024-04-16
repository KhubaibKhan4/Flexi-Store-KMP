package org.flexi.app

import org.flexi.app.domain.model.version.Platform
import java.awt.Desktop
import java.net.URI

internal actual fun openUrl(url: String?) {
    val uri = url?.let { URI.create(it) } ?: return
    Desktop.getDesktop().browse(uri)
}

actual fun getPlatform(): Platform {
    return Platform.Desktop
}