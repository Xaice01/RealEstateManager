package com.xavier_carpentier.realestatemanager.data.property

import com.xavier_carpentier.realestatemanager.datasource.property.PropertyDao
import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import com.xavier_carpentier.realestatemanager.domain.property.PropertyRepository
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyDomain
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class OfflinePropertyRepository @Inject constructor(private val propertyDao: PropertyDao) : PropertyRepository {


    override suspend fun insertProperty(propertyDomain: PropertyDomain):Long {
        return propertyDao.insert(PropertyMapper.mapToData(propertyDomain))
    }

    override suspend fun updateProperty(propertyDomain: PropertyDomain) {
        propertyDao.update(PropertyMapper.mapToData(propertyDomain))
    }

    override suspend fun deleteProperty(propertyDomain: PropertyDomain) {
        propertyDao.delete(PropertyMapper.mapToData(propertyDomain))
    }

    override fun getProperty(id: Int): Flow<PropertyDomain?> {
        return propertyDao.getProperty(id).map {
            property -> property.let { PropertyMapper.mapToDomain(it) }
        }
    }

    override fun getAllProperty(): Flow<List<PropertyDomain>> {
        return propertyDao.getAllProperty().map {
                property -> PropertyMapper.mapListToDomain(property)}
        }

    override fun getPropertyAndPicture(id: Int): Flow<PropertyWithPictureDomain?> {
        return propertyDao.getPropertyAndPicture(id).map {
            propertyWithPicture ->propertyWithPicture?.let { PropertyWithPictureMapper.mapToDomain(it) } }
        }

    override fun getAllPropertyAndPicture(): Flow<List<PropertyWithPictureDomain>?> {
        return propertyDao.getAllPropertyAndPicture().map{
            propertyWithPicture -> propertyWithPicture.let{PropertyWithPictureMapper.mapListToDomain(it)}
        }
    }

    override fun getFilteredPropertyAndPicture(filter: FilterDomain): Flow<List<PropertyWithPictureDomain>?> {
        val typesParameter: List<String>? = if (filter.types.isNullOrEmpty()) null else filter.types.map { it.databaseName }

        return if (typesParameter == null) {
            propertyDao.getFilteredPropertiesNoTypes(
                minPrice = filter.minPrice,
                maxPrice = filter.maxPrice,
                minSurface = filter.minSurface,
                maxSurface = filter.maxSurface,
                sold = filter.sold,
                nearbySchool = filter.nearbySchool,
                nearbyShop = filter.nearbyShop,
                nearbyPark = filter.nearbyPark,
                nearbyRestaurant = filter.nearbyRestaurant,
                nearbyPublicTransportation = filter.nearbyPublicTransportation,
                nearbyPharmacy = filter.nearbyPharmacy
            ).map {
                    propertyWithPicture -> propertyWithPicture.let{PropertyWithPictureMapper.mapListToDomain(it)}
            }
        } else {
            propertyDao.getFilteredPropertiesWithTypes(
                types = typesParameter,
                minPrice = filter.minPrice,
                maxPrice = filter.maxPrice,
                minSurface = filter.minSurface,
                maxSurface = filter.maxSurface,
                sold = filter.sold,
                nearbySchool = filter.nearbySchool,
                nearbyShop = filter.nearbyShop,
                nearbyPark = filter.nearbyPark,
                nearbyRestaurant = filter.nearbyRestaurant,
                nearbyPublicTransportation = filter.nearbyPublicTransportation,
                nearbyPharmacy = filter.nearbyPharmacy
            ).map {
                    propertyWithPicture -> propertyWithPicture.let{PropertyWithPictureMapper.mapListToDomain(it)}
            }
        }

    }

}