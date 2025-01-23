package com.spoony.spoony.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.spoony.spoony.R
import com.spoony.spoony.core.designsystem.theme.SpoonyAndroidTheme
import com.spoony.spoony.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition { false }
        setContent {
            SpoonyAndroidTheme {
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    delay(2000)

                    Intent(context, MainActivity::class.java).apply {
                        startActivity(this)
                    }
                    finish()
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SpoonyAndroidTheme.colors.black),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_tag_spoon_20),
                            contentDescription = null,
                            modifier = Modifier
                                .size(105.dp)
                                .padding(bottom = 8.dp)
                        )
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.img_spoony_logo_86),
                            contentDescription = null
                        )
                    }

                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
