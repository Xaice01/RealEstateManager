package com.xavier_carpentier.realestatemanager.domain.property

import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyDomain
import com.xavier_carpentier.realestatemanager.domain.property.model.PropertyWithPictureDomain
import kotlinx.coroutines.flow.Flow

interface PropertyRepository {

    suspend fun insertProperty(propertyDomain: PropertyDomain):Long

    suspend fun updateProperty(propertyDomain: PropertyDomain)

    suspend fun deleteProperty(propertyDomain: PropertyDomain)

    fun getProperty(id :Int): Flow<PropertyDomain?>

    fun getAllProperty() : Flow<List<PropertyDomain>>

    fun getPropertyAndPicture(id : Int): Flow<PropertyWithPictureDomain?>

    fun getAllPropertyAndPicture(): Flow<List<PropertyWithPictureDomain>?>
}