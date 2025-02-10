package com.xavier_carpentier.realestatemanager.domain.picture

import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain
import javax.inject.Inject

class InsertPictureUseCase @Inject constructor(
    private val pictureRepository: PictureRepository
) {
    suspend operator fun invoke(pictureDomain: PictureDomain) {
        pictureRepository.insertPicture(pictureDomain)
    }
}