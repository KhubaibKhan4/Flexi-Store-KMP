import androidx.compose.ui.window.ComposeUIViewController
import org.flexi.app.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
