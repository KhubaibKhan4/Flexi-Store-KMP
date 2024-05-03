package org.flexi.app

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import kotlinx.browser.window
import org.flexi.app.db.MyDatabase
import org.flexi.app.domain.model.version.Platform

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