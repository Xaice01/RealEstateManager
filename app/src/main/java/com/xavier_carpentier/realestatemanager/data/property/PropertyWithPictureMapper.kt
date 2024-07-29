package com.xavier_carpentier.realestatemanager.data.property

import com.xavier_carpentier.realestatemanager.data.picture.PictureMapper
import com.xavier_carpentier.realestatemanager.datasource.property.PropertyWithPicture
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain

object PropertyWithPictureMapper {



        fun mapToDomain(propertyWithPicture: PropertyWithPicture): PropertyWithPictureDomain{
            return PropertyWithPictureDomain(
                PropertyMapper.mapToDomain(propertyWithPicture.property),
                PictureMapper.mapListToDomain(propertyWithPicture.picture)
            )
        }

        fun mapListToDomain(propertiesWithPicture: List<PropertyWithPicture>) : List<PropertyWithPictureDomain> {
            return propertiesWithPicture.map {
                PropertyWithPictureDomain(
                    PropertyMapper.mapToDomain(it.property),
                    PictureMapper.mapListToDomain(it.picture)) }
        }

        fun mapToData(propertyWithPictureDomain: PropertyWithPictureDomain) :PropertyWithPicture{
            return PropertyWithPicture(
                PropertyMapper.mapToData(propertyWithPictureDomain.propertyDomain),
                PictureMapper.mapListToData(propertyWithPictureDomain.pictureDomains)
            )
        }

        fun mapListToData(propertiesWithPictureDomain: List<PropertyWithPictureDomain>) : List<PropertyWithPicture>{
            return propertiesWithPictureDomain.map {
                PropertyWithPicture(
                    PropertyMapper.mapToData(it.propertyDomain),
                    PictureMapper.mapListToData(it.pictureDomains)
                )
            }
        }
}