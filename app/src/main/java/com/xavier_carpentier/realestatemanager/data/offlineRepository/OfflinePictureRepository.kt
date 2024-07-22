package com.xavier_carpentier.realestatemanager.data.offlineRepository

import com.xavier_carpentier.realestatemanager.datasource.picture.Picture
import com.xavier_carpentier.realestatemanager.datasource.picture.PictureDao
import com.xavier_carpentier.realestatemanager.datasource.picture.PictureRepository
import kotlinx.coroutines.flow.Flow

//TODO mettre une injection avec hilt
class OfflinePictureRepository(private val pictureDao: PictureDao) :PictureRepository {
    override suspend fun insertPicture(picture: Picture) {
        pictureDao.insert(picture)
    }

    override suspend fun updatePicture(picture: Picture) {
        pictureDao.update(picture)
    }

    override suspend fun deletePicture(picture: Picture) {
        pictureDao.delete(picture)
    }

    override fun getPicture(id: Int): Flow<Picture?> {
        return pictureDao.getPicture(id)
    }

    override fun getAllPicturesByPropertyId(propertyId: Int): Flow<List<Picture>?> {
        return pictureDao.getPicturesByProperty(propertyId)
    }

    override fun getAllPicture(): Flow<List<Picture>> {
        return pictureDao.getAllPicture()
    }


}