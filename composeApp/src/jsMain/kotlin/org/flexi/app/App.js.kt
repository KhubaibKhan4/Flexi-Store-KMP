package org.flexi.app

import app.cash.sqldelight.db.SqlDriver
import kotlinx.browser.window
import org.flexi.app.domain.model.version.Platform

internal actual fun openUrl(url: String?) {
    url?.let { window.open(it) }
}

actual fun getPlatform(): Platform {
    return Platform.Web
}

actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        TODO("Not yet implemented")
    }
}