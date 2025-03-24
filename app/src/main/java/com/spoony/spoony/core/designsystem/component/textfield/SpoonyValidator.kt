package com.spoony.spoony.core.designsystem.component.textfield

object SpoonyValidator {
    private val emojiPattern = Regex("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]")
    private val specialCharPattern = Regex("[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ ]")

    fun isNotContainsEmoji(input: String): Boolean {
        return !emojiPattern.containsMatchIn(input)
    }

    fun isNotContainsSpecialChars(input: String): Boolean {
        return specialCharPattern.find(input) == null
    }
}
