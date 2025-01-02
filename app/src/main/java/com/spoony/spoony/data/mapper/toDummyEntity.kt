package com.spoony.spoony.data.mapper

import com.spoony.spoony.data.dto.response.GetDummyUserResponse
import com.spoony.spoony.domain.entity.DummyEntity

fun GetDummyUserResponse.toDummyEntity() =
    DummyEntity(
        id = this.data.id,
        firstName = this.data.firstName,
        avatar = this.data.avatar
    )
