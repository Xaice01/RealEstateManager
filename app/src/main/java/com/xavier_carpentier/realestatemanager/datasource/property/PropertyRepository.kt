package com.xavier_carpentier.realestatemanager.datasource.property

import kotlinx.coroutines.flow.Flow

interface PropertyRepository {

    suspend fun insertProperty(property:Property)

    suspend fun updateProperty(property: Property)

    suspend fun deleteProperty(property: Property)

    fun getProperty(id :Int): Flow<Property?>

    fun getAllProperty() : Flow<List<Property>>

    fun getPropertyAndPicture(id : Int): Flow<PropertyWithPicture?>

    fun getAllPropertyAndPicture(): Flow<PropertyWithPicture>
}