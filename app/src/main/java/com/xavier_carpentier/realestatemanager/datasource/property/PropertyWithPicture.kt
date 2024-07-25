package com.xavier_carpentier.realestatemanager.datasource.property

import androidx.room.Embedded
import androidx.room.Relation
import com.xavier_carpentier.realestatemanager.datasource.picture.Picture

data class PropertyWithPicture(
    @Embedded
    val property: Property,
    @Relation(
        parentColumn = "id",
        entityColumn = "propertyId"
    ) val picture: List<Picture>
)
