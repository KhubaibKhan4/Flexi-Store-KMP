package org.flexi.app

import app.cash.sqldelight.db.SqlDriver
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

actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        TODO("Not yet implemented")
    }
}