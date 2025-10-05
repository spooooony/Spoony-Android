package com.spoony.spoony.presentation.explore.extension

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import kotlinx.collections.immutable.PersistentMap

fun <K, V> PersistentMap<K, V>.toMutableStateMap(): SnapshotStateMap<K, V> {
    return mutableStateMapOf<K, V>().apply {
        putAll(this@toMutableStateMap)
    }
}

fun <K> SnapshotStateMap<K, Boolean>.toggle(id: K) {
    this[id] = !(this[id] ?: false)
}
