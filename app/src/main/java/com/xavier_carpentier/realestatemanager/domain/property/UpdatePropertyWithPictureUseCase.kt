package com.xavier_carpentier.realestatemanager.domain.property

import com.xavier_carpentier.realestatemanager.domain.picture.PictureRepository
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import javax.inject.Inject

class UpdatePropertyWithPictureUseCase @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val pictureRepository: PictureRepository
) {
    suspend operator fun invoke(propertyWithPictureDomain: PropertyWithPictureDomain) {
        propertyRepository.updateProperty(propertyWithPictureDomain.propertyDomain)  // todo modified by current currency dollar: do nothing euro: convert price

        for (picture in propertyWithPictureDomain.pictureDomains) {
            pictureRepository.upsertPicture(picture)
        }
    }

}