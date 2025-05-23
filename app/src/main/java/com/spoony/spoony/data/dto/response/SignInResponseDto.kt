package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponseDto(
    @SerialName("exists")
    val exists: Boolean,
    @SerialName("user")
    val user: UserDto?,
    @SerialName("jwtTokenDto")
    val jwtTokenDto: TokenResponseDto?
) {
    @Serializable
    data class UserDto(
        @SerialName("userId")
        val userId: Int,
        @SerialName("platform")
        val platform: String,
        @SerialName("platformId")
        val plaformId: String,
        @SerialName("imageLevel")
        val imageLevel: Int,
        @SerialName("level")
        val level: Int,
        @SerialName("region")
        val region: GetRegionResponseDto,
        @SerialName("introduction")
        val introduction: String,
        @SerialName("birth")
        val birth: String,
        @SerialName("ageGroup")
        val ageGroup: String,
        @SerialName("createdAt")
        val createdAt: String,
        @SerialName("updatedAt")
        val updatedAt: String
    )
}
