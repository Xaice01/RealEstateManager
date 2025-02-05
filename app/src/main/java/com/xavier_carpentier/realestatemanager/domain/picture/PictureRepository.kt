package com.xavier_carpentier.realestatemanager.domain.picture

import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain
import kotlinx.coroutines.flow.Flow

interface PictureRepository {

    suspend fun insertPicture(pictureDomain: PictureDomain)

    suspend fun updatePicture(pictureDomain: PictureDomain)

    suspend fun upsertPicture(pictureDomain: PictureDomain)

    suspend fun deletePicture(pictureDomain: PictureDomain)

    fun getPicture(id :Int): Flow<PictureDomain?>

    fun getAllPicturesByPropertyId(propertyId :Int) :Flow<List<PictureDomain>?>

    fun getAllPicture() : Flow<List<PictureDomain>>
}