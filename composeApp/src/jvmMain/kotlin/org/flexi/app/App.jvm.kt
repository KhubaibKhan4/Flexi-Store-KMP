package org.flexi.app

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.flexi.app.db.MyDatabase
import org.flexi.app.domain.model.version.Platform
import java.awt.Desktop
import java.io.File
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
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        if (!File("YouTubeDatabase.db").exists()) {
            MyDatabase.Schema.create(driver)
        }
        return driver
    }
}