package com.xavier_carpentier.realestatemanager.data.picture

import com.xavier_carpentier.realestatemanager.datasource.picture.Picture
import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain

object PictureMapper {


        fun mapToDomain(picture: Picture) :PictureDomain{
            return PictureDomain(
                picture.id,
                picture.propertyId,
                picture.description,
                picture.imageData)
        }

        fun mapListToDomain(pictures: List<Picture>) :List<PictureDomain>{
            return pictures.map {
                PictureDomain(
                    it.id,
                    it.propertyId,
                    it.description,
                    it.imageData)
            }
        }

        fun mapToData(pictureDomain: PictureDomain) :Picture{
            return Picture(
                pictureDomain.id,
                pictureDomain.propertyId,
                pictureDomain.description,
                pictureDomain.image)
        }

        fun mapListToData(picturesDomain: List<PictureDomain>) :List<Picture>{
            return picturesDomain.map{
                Picture(
                    it.id,
                    it.propertyId,
                    it.description,
                    it.image)
            }
        }

}