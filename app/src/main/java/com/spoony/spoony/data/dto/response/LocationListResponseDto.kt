package com.spoony.spoony.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationListResponseDto(
    @SerialName("locationResponseList")
    val locationResponseList: List<LocationResponseDto>
) {
    @Serializable
    data class LocationResponseDto(
        @SerialName("locationId")
        val locationId: Int,
        @SerialName("locationName")
        val locationName: String,
        @SerialName("locationAddress")
        val locationAddress: String,
        @SerialName("locationType")
        val locationType: LocationType,
        @SerialName("longitude")
        val longitude: Double,
        @SerialName("latitude")
        val latitude: Double
    ) {
        @Serializable
        data class LocationType(
            @SerialName("locationTypeId")
            val locationTypeId: Int,
            @SerialName("locationTypeName")
            val locationTypeName: String,
            @SerialName("scope")
            val scope: Double
        )
    }
}
