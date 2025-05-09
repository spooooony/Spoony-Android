package com.spoony.spoony.presentation.attendance.model

data class SpoonDrawModel(
    val drawId: Int,
    val spoonTypeId: Int,
    val spoonName: String,
    val spoonAmount: Int,
    val spoonImage: String,
    val localDate: String
)
