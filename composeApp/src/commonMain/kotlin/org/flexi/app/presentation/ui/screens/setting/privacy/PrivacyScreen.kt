package org.flexi.app.presentation.ui.screens.setting.privacy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class PrivacyScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Privacy & Policy",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigator?.pop()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = padding.calculateTopPadding(), bottom = 34.dp, start = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                privacyPolicyText.split("\n\n").forEach { paragraph ->
                    val isTitle = paragraph.startsWith("**") && paragraph.endsWith("**")
                    val text = if (isTitle) paragraph.removePrefix("**").removeSuffix("**") else paragraph

                    Text(
                        text = text.trim(),
                        fontSize = if (isTitle) 18.sp else 16.sp,
                        fontWeight = if (isTitle) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }

    private val privacyPolicyText = """
**Privacy Policy for Flexi-Store-KMP**

**Effective Date: [Date]**

This Privacy Policy describes how Flexi-Store-KMP ("we", "us", or "our") collects, uses, and discloses information about you when you use our mobile application (the "App").

**Information We Collect**

We collect the following information from you when you use the App:

* **Personal Information:** We collect your name, email address, and shipping address when you create an account. We also collect your payment information when you make a purchase.
* **Device Information:** We collect information about your device, such as your device model, operating system version, and IP address.
* **Usage Information:** We collect information about how you use the App, such as the pages you visit, the products you view, and the purchases you make.

**Use of Information**

We use the information we collect to:

* Provide and improve the App.
* Process your orders and payments.
* Communicate with you about your orders and the App.
* Personalize your experience on the App.
* Prevent fraud and abuse.

**Disclosure of Information**

We may disclose your information to the following third parties:

* Service providers who help us operate the App, such as payment processors and shipping companies.
* Law enforcement or other government agencies, if required by law.

**Security**

We take reasonable steps to protect your information from unauthorized access, use, or disclosure. However, no security measures are perfect, and we cannot guarantee the security of your information.

**Your Choices**

You can choose to opt out of certain uses of your information, such as marketing communications. You can also request to access, correct, or delete your information. To make these requests, please contact us at [email protected]

**Changes to this Privacy Policy**

We may update this Privacy Policy from time to time. We will post the updated Privacy Policy on the App and update the "Effective Date" at the top of this Policy.

**Contact Us**

If you have any questions about this Privacy Policy, please contact us at [email protected]
""".trimIndent()
}


