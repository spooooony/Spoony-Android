package com.spoony.spoony.domain.entity

data class SpoonEntity(
    val drawId: Int,
    val spoonType: SpoonType,
    val localDate: String,
    val weekStartDate: String,
    val createdAt: String
) {
    data class SpoonType(
        val spoonTypeId: Int,
        val spoonName: String,
        val spoonAmount: Int,
        val probability: Int,
        val spoonImage: String
    )
}
