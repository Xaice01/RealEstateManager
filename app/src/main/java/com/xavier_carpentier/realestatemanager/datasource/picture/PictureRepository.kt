package com.xavier_carpentier.realestatemanager.datasource.picture

import kotlinx.coroutines.flow.Flow

interface PictureRepository {

    suspend fun insertPicture(picture: Picture)

    suspend fun updatePicture(picture: Picture)

    suspend fun deletePicture(picture: Picture)

    fun getPicture(id :Int): Flow<Picture?>

    fun getAllPicturesByPropertyId(propertyId :Int) :Flow<List<Picture>?>

    fun getAllPicture() : Flow<List<Picture>>
}