package com.xavier_carpentier.realestatemanager.data.offlineRepository

import com.xavier_carpentier.realestatemanager.datasource.property.Property
import com.xavier_carpentier.realestatemanager.datasource.property.PropertyDao
import com.xavier_carpentier.realestatemanager.datasource.property.PropertyRepository
import com.xavier_carpentier.realestatemanager.datasource.property.PropertyWithPicture
import kotlinx.coroutines.flow.Flow

//TODO mettre une injection avec hilt
class OfflinePropertyRepository(private val propertyDao: PropertyDao) :PropertyRepository {

    override suspend fun insertProperty(property: Property) {
        propertyDao.insert(property)
    }

    override suspend fun updateProperty(property: Property) {
        propertyDao.update(property)
    }

    override suspend fun deleteProperty(property: Property) {
        propertyDao.delete(property)
    }

    override fun getProperty(id: Int): Flow<Property?> {
        return propertyDao.getProperty(id)
    }

    override fun getAllProperty(): Flow<List<Property>> {
        return propertyDao.getAllProperty()
    }

    override fun getPropertyAndPicture(id: Int): Flow<PropertyWithPicture?> {
        return propertyDao.getPropertyAndPicture(id)
    }

    override fun getAllPropertyAndPicture(): Flow<PropertyWithPicture> {
        return propertyDao.getAllPropertyAndPicture()
    }

}