package com.spoony.spoony.core.analytics

import android.content.Context
import com.mixpanel.android.mpmetrics.MixpanelAPI
import com.spoony.spoony.BuildConfig.MIXPANEL_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import org.json.JSONObject
import timber.log.Timber

class MixPanelTracker @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val mixpanel = MixpanelAPI.getInstance(
        context,
        MIXPANEL_KEY,
        false
    )

    fun setUserProfile(userId: String, properties: Map<String, Any>) {
        mixpanel.identify(userId)
        properties.forEach { (key, value) ->
            mixpanel.people.set(key, value)
        }
    }

    fun resetUserProfile() {
        mixpanel.reset()
    }

    fun track(eventName: String) {
        Timber.tag("mixpanel").d(eventName)
        mixpanel.track(eventName)
    }

    fun track(eventName: String, properties: JSONObject) {
        Timber.tag("mixpanel").d("$eventName $properties")
        mixpanel.track(eventName, properties)
    }
}
