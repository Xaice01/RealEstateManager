package com.xavier_carpentier.realestatemanager.domain.property

import com.xavier_carpentier.realestatemanager.domain.current_setting.utils.ConversePrice
import com.xavier_carpentier.realestatemanager.domain.picture.PictureRepository
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import javax.inject.Inject

class InsertPropertyWithPictureUseCase @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val pictureRepository: PictureRepository,
    private val conversePrice: ConversePrice
) {

    suspend operator fun invoke(propertyDomain: PropertyWithPictureDomain) : Boolean{
        val idProperty = propertyRepository.insertProperty(
            propertyDomain.propertyDomain.copy(price = conversePrice.conversePriceToData(propertyDomain.propertyDomain.price))
        )

        for (picture in propertyDomain.pictureDomains) {
            pictureRepository.insertPicture(picture.copy(propertyId = idProperty.toInt(), id = 0))
        }
        return idProperty > 0
    }

}