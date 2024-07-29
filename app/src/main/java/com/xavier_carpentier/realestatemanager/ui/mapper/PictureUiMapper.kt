package com.xavier_carpentier.realestatemanager.ui.mapper

import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain
import com.xavier_carpentier.realestatemanager.ui.model.PictureUi

object PictureUiMapper {

    fun mapToUi(picture: PictureDomain) : PictureUi {
        return PictureUi(
            picture.id,
            picture.propertyId,
            picture.description,
            picture.image)
    }

    fun mapListToUi(pictures: List<PictureDomain>) :List<PictureUi>{
        return pictures.map {
            PictureUi(
                it.id,
                it.propertyId,
                it.description,
                it.image)
        }
    }

    fun mapToDomain(pictureUi: PictureUi) : PictureDomain {
        return PictureDomain(
            pictureUi.id,
            pictureUi.propertyId,
            pictureUi.description,
            pictureUi.image)
    }

    fun mapListToDomain(picturesUi: List<PictureUi>) :List<PictureDomain>{
        return picturesUi.map{
            PictureDomain(
                it.id,
                it.propertyId,
                it.description,
                it.image)
        }
    }
}