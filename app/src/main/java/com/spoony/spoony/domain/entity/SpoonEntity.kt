package com.spoony.spoony.domain.entity

data class SpoonListEntity(
    val spoonResultList: List<SpoonEntity>,
    val totalSpoonCount: Int,
    val weeklySpoonCount: Int
)

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
        val probability: Double,
        val spoonImage: String
    )
}
