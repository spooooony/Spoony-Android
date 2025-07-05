package com.spoony.spoony.core.analytics

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf
import com.mixpanel.android.mpmetrics.MixpanelAPI
import com.spoony.spoony.BuildConfig.MIXPANEL_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import org.json.JSONObject

val LocalTracker = staticCompositionLocalOf<MixPanelTracker> {
    error("No MixpanelTracker provided")
}

class MixPanelTracker @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val mixpanel = MixpanelAPI.getInstance(
        context,
        MIXPANEL_KEY,
        false
    )

    fun track(eventName: String) {
        mixpanel.track(eventName)
    }

    fun track(eventName: String, properties: String) {
        mixpanel.track(eventName, properties.toJsonObject())
    }

    private fun String.toJsonObject(): JSONObject {
        return try {
            JSONObject(this)
        } catch (e: Exception) {
            JSONObject()
        }
    }
}
