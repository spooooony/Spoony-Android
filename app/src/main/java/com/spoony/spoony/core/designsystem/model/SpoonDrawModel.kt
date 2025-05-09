package com.spoony.spoony.core.designsystem.model

data class SpoonDrawModel(
    val drawId: Int = -1,
    val spoonTypeId: Int = -1,
    val spoonName: String = "",
    val spoonAmount: Int = -1,
    val spoonImage: String = "",
    val localDate: String = ""
)
