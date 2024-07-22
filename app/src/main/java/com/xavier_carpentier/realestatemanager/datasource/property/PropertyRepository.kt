package com.xavier_carpentier.realestatemanager.datasource.property

import com.xavier_carpentier.realestatemanager.datasource.picture.Picture
import kotlinx.coroutines.flow.Flow

interface PropertyRepository {

    suspend fun insertProperty(property:Property)

    suspend fun updateProperty(property: Property)

    suspend fun deleteProperty(property: Property)

    fun getProperty(id :Int): Flow<Property?>

    fun getAllProperty() : Flow<List<Property>>

    fun getPropertyAndPicture(id : Int): Flow<Map<Property, List<Picture>>?>

    fun getAllPropertyAndPicture(): Flow<List<Map<Property, List<Picture>>>>
}