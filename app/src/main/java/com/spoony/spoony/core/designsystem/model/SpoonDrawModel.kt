package com.spoony.spoony.core.designsystem.model

data class SpoonDrawModel(
    val drawId: Int,
    val spoonTypeId: Int,
    val spoonName: String,
    val spoonAmount: Int,
    val spoonImage: String,
    val localDate: String
) {
    companion object {
        val DEFAULT = SpoonDrawModel(
            drawId = -1,
            spoonTypeId = -1,
            spoonName = "",
            spoonAmount = -1,
            spoonImage = "",
            localDate = ""
        )
    }
}
