package com.xavier_carpentier.realestatemanager.data.picture

import com.xavier_carpentier.realestatemanager.datasource.picture.PictureDao
import com.xavier_carpentier.realestatemanager.domain.picture.PictureRepository
import com.xavier_carpentier.realestatemanager.domain.picture.model.PictureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflinePictureRepository @Inject constructor(private val pictureDao : PictureDao) : PictureRepository {


    override suspend fun insertPicture(pictureDomain: PictureDomain) {
        pictureDao.insert(PictureMapper.mapToData(pictureDomain))
    }

    override suspend fun updatePicture(pictureDomain: PictureDomain) {
        pictureDao.update(PictureMapper.mapToData(pictureDomain))
    }

    override suspend fun deletePicture(pictureDomain: PictureDomain) {
        pictureDao.delete(PictureMapper.mapToData(pictureDomain))
    }

    override fun getPicture(id: Int): Flow<PictureDomain?> {
        return pictureDao.getPicture(id).map {
            picture -> picture?.let { PictureMapper.mapToDomain(it) }
        }
    }

    override fun getAllPicturesByPropertyId(propertyId: Int): Flow<List<PictureDomain>?> {
        return pictureDao.getPicturesByProperty(propertyId).map {
            pictures -> pictures?.let {PictureMapper.mapListToDomain(it)}
        }
    }

    override fun getAllPicture(): Flow<List<PictureDomain>> {
        return pictureDao.getAllPicture().map {
            pictures-> PictureMapper.mapListToDomain(pictures)
        }
    }


}