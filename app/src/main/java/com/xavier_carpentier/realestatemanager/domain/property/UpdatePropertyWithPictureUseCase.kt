package com.xavier_carpentier.realestatemanager.domain.property

import com.xavier_carpentier.realestatemanager.domain.current_setting.utils.ConversePrice
import com.xavier_carpentier.realestatemanager.domain.picture.PictureRepository
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import javax.inject.Inject

class UpdatePropertyWithPictureUseCase @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val pictureRepository: PictureRepository,
    private val conversePrice: ConversePrice
) {
    suspend operator fun invoke(propertyWithPictureDomain: PropertyWithPictureDomain) {
        propertyRepository.updateProperty(
            propertyWithPictureDomain.propertyDomain.copy(price = conversePrice.conversePriceToData(propertyWithPictureDomain.propertyDomain.price))
        )

        for (picture in propertyWithPictureDomain.pictureDomains) {
            pictureRepository.upsertPicture(picture)
        }
    }

}