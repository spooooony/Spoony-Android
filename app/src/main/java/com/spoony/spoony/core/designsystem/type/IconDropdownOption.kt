package com.spoony.spoony.core.designsystem.type

sealed class IconDropdownOption(
    val text: String,
    val type: String
) {
    class Option(text: String, type: String) : IconDropdownOption(text, type)
}
