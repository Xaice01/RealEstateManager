package com.xavier_carpentier.realestatemanager.domain.property.model

import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain

data class PropertyWithPictureDomain(
    val propertyDomain: PropertyDomain,
    val pictureDomains :List<PictureDomain>
)
