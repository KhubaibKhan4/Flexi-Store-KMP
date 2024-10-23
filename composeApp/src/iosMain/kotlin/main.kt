import androidx.compose.ui.window.ComposeUIViewController
import org.flexi.app.App
import org.flexi.app.di.appModule
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(configure = {enforceStrictPlistSanityCheck = false}) { App() }