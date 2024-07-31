package com.xavier_carpentier.realestatemanager

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xavier_carpentier.realestatemanager.datasource.database.DatabaseRealEstateManager
import com.xavier_carpentier.realestatemanager.datasource.picture.PictureDao
import com.xavier_carpentier.realestatemanager.datasource.property.Property
import com.xavier_carpentier.realestatemanager.datasource.property.PropertyDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var database: DatabaseRealEstateManager
    private lateinit var propertyDao: PropertyDao
    private lateinit var pictureDao: PictureDao

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                DatabaseRealEstateManager::class.java)
            .build()

        propertyDao=database.propertyDao()
        pictureDao=database.pictureDao()
    }

    fun createProperty() :Property{
        return Property(
            type="Villa",
            price=100000,
            address="33 east street NewYork, NY,USA",
            latitude=40.71248320854847,
            longitude=-74.00603269565413,
            surface=300,
            room=7,
            bedroom=3,
            description="The description of the property",
            entryDate= Calendar.getInstance(),
            soldDate=null,
            sold=false,
            agent="Lama",
            interestNearbySchool=true,
            interestNearbyShop=true,
            interestNearbyPark=true,
            interestNearbyRestaurant=false,
            interestNearbyPublicTransportation=true,
            interestNearbyPharmacy=false)
    }

    @Test
    fun testInsertproperty() = runBlocking{
        val property =createProperty()
        propertyDao.insert(property)

        val properties =propertyDao.getAllProperty()

        properties.collectLatest {
            assertEquals(1,it.size)
            assertEquals(property,it[0])
        }
    }

    @After
    fun tearDown(){
        database.close()
    }
}