package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import com.xavier_carpentier.realestatemanager.ui.model.PropertyWithPictureUi

object PropertyWithPictureUiMapper {

    fun mapToUi(propertyWithPicture: PropertyWithPictureDomain) : PropertyWithPictureUi {
        return PropertyWithPictureUi(
            PropertyUiMapper.mapToUi(propertyWithPicture.propertyDomain),
            PictureUiMapper.mapListToUi(propertyWithPicture.pictureDomains)
        )
    }

    fun mapListToUi(propertyWithPictures: List<PropertyWithPictureDomain>) :List<PropertyWithPictureUi>{
        return propertyWithPictures.map{
            PropertyWithPictureUi(
                PropertyUiMapper.mapToUi(it.propertyDomain),
                PictureUiMapper.mapListToUi(it.pictureDomains)
            )
        }
    }

    fun mapToDomain(propertyWithPictureUi: PropertyWithPictureUi) : PropertyWithPictureDomain {
        return PropertyWithPictureDomain(
                PropertyUiMapper.mapToDomain(propertyWithPictureUi.propertyUi),
                PictureUiMapper.mapListToDomain(propertyWithPictureUi.picturesUi)
        )
    }

    fun mapListToDomain(propertyWithPicturesUi: List<PropertyWithPictureUi>) :List<PropertyWithPictureDomain>{
        return propertyWithPicturesUi.map{
            PropertyWithPictureDomain(
                PropertyUiMapper.mapToDomain(it.propertyUi),
                PictureUiMapper.mapListToDomain(it.picturesUi)
            )
        }
    }
}