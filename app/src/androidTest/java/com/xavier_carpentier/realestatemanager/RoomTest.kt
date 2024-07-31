package com.xavier_carpentier.realestatemanager

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xavier_carpentier.realestatemanager.datasource.database.DatabaseRealEstateManager
import com.xavier_carpentier.realestatemanager.datasource.picture.Picture
import com.xavier_carpentier.realestatemanager.datasource.picture.PictureDao
import com.xavier_carpentier.realestatemanager.datasource.property.Property
import com.xavier_carpentier.realestatemanager.datasource.property.PropertyDao
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
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

    fun createProperty1() :Property{
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

    fun createProperty2() :Property{
        return Property(
            type="House",
            price=800000,
            address="35 east street NewYork, NY,USA",
            latitude=40.81248320854847,
            longitude=-74.00603269565413,
            surface=100,
            room=7,
            bedroom=3,
            description="The description of the property2",
            entryDate= Calendar.getInstance(),
            soldDate=Calendar.getInstance(),
            sold=true,
            agent="Cat",
            interestNearbySchool=true,
            interestNearbyShop=false,
            interestNearbyPark=false,
            interestNearbyRestaurant=true,
            interestNearbyPublicTransportation=true,
            interestNearbyPharmacy=false)
    }

    fun createProperty3() :Property{
        return Property(
            type="Duplex",
            price=950000,
            address="2 east street NewYork, NY,USA",
            latitude=40.11248320854847,
            longitude=-74.00603269565413,
            surface=50,
            room=5,
            bedroom=2,
            description="The description of the property3",
            entryDate= Calendar.getInstance(),
            soldDate=null,
            sold=false,
            agent="Lama",
            interestNearbySchool=false,
            interestNearbyShop=false,
            interestNearbyPark=true,
            interestNearbyRestaurant=true,
            interestNearbyPublicTransportation=true,
            interestNearbyPharmacy=false)
    }

    @Test
    fun testInsertProperty() = runBlocking{
        val property =createProperty1()
        val insertedId = propertyDao.insert(property)

        val properties =propertyDao.getAllProperty().first()


        assertEquals(1, properties.size)
        val retrievedProperty = properties[0]
        assertEquals(insertedId.toInt(), retrievedProperty.id)
        assertEquals(property.type, retrievedProperty.type)
        assertEquals(property.price, retrievedProperty.price)
        assertEquals(property.address, retrievedProperty.address)
        assertEquals(property.latitude, retrievedProperty.latitude)
        assertEquals(property.longitude, retrievedProperty.longitude)
        assertEquals(property.surface, retrievedProperty.surface)
        assertEquals(property.room, retrievedProperty.room)
        assertEquals(property.bedroom, retrievedProperty.bedroom)
        assertEquals(property.description, retrievedProperty.description)
        assertEquals(property.entryDate, retrievedProperty.entryDate)
        assertEquals(property.soldDate, retrievedProperty.soldDate)
        assertEquals(property.sold, retrievedProperty.sold)
        assertEquals(property.agent, retrievedProperty.agent)
        assertEquals(property.interestNearbySchool, retrievedProperty.interestNearbySchool)
        assertEquals(property.interestNearbyShop, retrievedProperty.interestNearbyShop)
        assertEquals(property.interestNearbyPark, retrievedProperty.interestNearbyPark)
        assertEquals(property.interestNearbyRestaurant, retrievedProperty.interestNearbyRestaurant)
        assertEquals(property.interestNearbyPublicTransportation, retrievedProperty.interestNearbyPublicTransportation)
        assertEquals(property.interestNearbyPharmacy, retrievedProperty.interestNearbyPharmacy)

    }

    @Test
    fun testManyInsertProperty() = runBlocking{

        val property1 =createProperty1()
        val property2 =createProperty2()
        val property3 =createProperty3()
        val insertedProperty1Id = propertyDao.insert(property1)
        val insertedProperty2Id = propertyDao.insert(property2)
        val insertedProperty3Id = propertyDao.insert(property3)

        //list order by sold
        val properties =propertyDao.getAllProperty().first()

        assertEquals(3, properties.size)

        //order by sold
        val retrievedProperty1 = properties[0]
        assertEquals(insertedProperty1Id.toInt(), retrievedProperty1.id)
        assertEquals(property1.type, retrievedProperty1.type)
        assertEquals(property1.price, retrievedProperty1.price)
        assertEquals(property1.address, retrievedProperty1.address)
        assertEquals(property1.latitude, retrievedProperty1.latitude)
        assertEquals(property1.longitude, retrievedProperty1.longitude)
        assertEquals(property1.surface, retrievedProperty1.surface)
        assertEquals(property1.room, retrievedProperty1.room)
        assertEquals(property1.bedroom, retrievedProperty1.bedroom)
        assertEquals(property1.description, retrievedProperty1.description)
        assertEquals(property1.entryDate, retrievedProperty1.entryDate)
        assertEquals(property1.soldDate, retrievedProperty1.soldDate)
        assertEquals(property1.sold, retrievedProperty1.sold)
        assertEquals(property1.agent, retrievedProperty1.agent)
        assertEquals(property1.interestNearbySchool, retrievedProperty1.interestNearbySchool)
        assertEquals(property1.interestNearbyShop, retrievedProperty1.interestNearbyShop)
        assertEquals(property1.interestNearbyPark, retrievedProperty1.interestNearbyPark)
        assertEquals(property1.interestNearbyRestaurant, retrievedProperty1.interestNearbyRestaurant)
        assertEquals(property1.interestNearbyPublicTransportation, retrievedProperty1.interestNearbyPublicTransportation)
        assertEquals(property1.interestNearbyPharmacy, retrievedProperty1.interestNearbyPharmacy)

        val retrievedProperty2 = properties[2]
        assertEquals(insertedProperty2Id.toInt(), retrievedProperty2.id)
        assertEquals(property2.type, retrievedProperty2.type)
        assertEquals(property2.price, retrievedProperty2.price)
        assertEquals(property2.address, retrievedProperty2.address)
        assertEquals(property2.latitude, retrievedProperty2.latitude)
        assertEquals(property2.longitude, retrievedProperty2.longitude)
        assertEquals(property2.surface, retrievedProperty2.surface)
        assertEquals(property2.room, retrievedProperty2.room)
        assertEquals(property2.bedroom, retrievedProperty2.bedroom)
        assertEquals(property2.description, retrievedProperty2.description)
        assertEquals(property2.entryDate, retrievedProperty2.entryDate)
        assertEquals(property2.soldDate, retrievedProperty2.soldDate)
        assertEquals(property2.sold, retrievedProperty2.sold)
        assertEquals(property2.agent, retrievedProperty2.agent)
        assertEquals(property2.interestNearbySchool, retrievedProperty2.interestNearbySchool)
        assertEquals(property2.interestNearbyShop, retrievedProperty2.interestNearbyShop)
        assertEquals(property2.interestNearbyPark, retrievedProperty2.interestNearbyPark)
        assertEquals(property2.interestNearbyRestaurant, retrievedProperty2.interestNearbyRestaurant)
        assertEquals(property2.interestNearbyPublicTransportation, retrievedProperty2.interestNearbyPublicTransportation)
        assertEquals(property2.interestNearbyPharmacy, retrievedProperty2.interestNearbyPharmacy)

        val retrievedProperty3 = properties[1]
        assertEquals(insertedProperty3Id.toInt(), retrievedProperty3.id)
        assertEquals(property3.type, retrievedProperty3.type)
        assertEquals(property3.price, retrievedProperty3.price)
        assertEquals(property3.address, retrievedProperty3.address)
        assertEquals(property3.latitude, retrievedProperty3.latitude)
        assertEquals(property3.longitude, retrievedProperty3.longitude)
        assertEquals(property3.surface, retrievedProperty3.surface)
        assertEquals(property3.room, retrievedProperty3.room)
        assertEquals(property3.bedroom, retrievedProperty3.bedroom)
        assertEquals(property3.description, retrievedProperty3.description)
        assertEquals(property3.entryDate, retrievedProperty3.entryDate)
        assertEquals(property3.soldDate, retrievedProperty3.soldDate)
        assertEquals(property3.sold, retrievedProperty3.sold)
        assertEquals(property3.agent, retrievedProperty3.agent)
        assertEquals(property3.interestNearbySchool, retrievedProperty3.interestNearbySchool)
        assertEquals(property3.interestNearbyShop, retrievedProperty3.interestNearbyShop)
        assertEquals(property3.interestNearbyPark, retrievedProperty3.interestNearbyPark)
        assertEquals(property3.interestNearbyRestaurant, retrievedProperty3.interestNearbyRestaurant)
        assertEquals(property3.interestNearbyPublicTransportation, retrievedProperty3.interestNearbyPublicTransportation)
        assertEquals(property3.interestNearbyPharmacy, retrievedProperty3.interestNearbyPharmacy)
    }

    @Test
    fun testGetProperty() = runBlocking {
        val property1 = createProperty1()
        val property2 = createProperty2()
        val insertedId = propertyDao.insert(property1)
        propertyDao.insert(property2)

        val retrievedProperty = propertyDao.getProperty(insertedId.toInt()).first()

        assertEquals(insertedId.toInt(), retrievedProperty.id)
        assertEquals(property1.type, retrievedProperty.type)
        assertEquals(property1.price, retrievedProperty.price)
        assertEquals(property1.address, retrievedProperty.address)
        assertEquals(property1.latitude, retrievedProperty.latitude)
        assertEquals(property1.longitude, retrievedProperty.longitude)
        assertEquals(property1.surface, retrievedProperty.surface)
        assertEquals(property1.room, retrievedProperty.room)
        assertEquals(property1.bedroom, retrievedProperty.bedroom)
        assertEquals(property1.description, retrievedProperty.description)
        assertEquals(property1.entryDate, retrievedProperty.entryDate)
        assertEquals(property1.soldDate, retrievedProperty.soldDate)
        assertEquals(property1.sold, retrievedProperty.sold)
        assertEquals(property1.agent, retrievedProperty.agent)
        assertEquals(property1.interestNearbySchool, retrievedProperty.interestNearbySchool)
        assertEquals(property1.interestNearbyShop, retrievedProperty.interestNearbyShop)
        assertEquals(property1.interestNearbyPark, retrievedProperty.interestNearbyPark)
        assertEquals(property1.interestNearbyRestaurant, retrievedProperty.interestNearbyRestaurant)
        assertEquals(property1.interestNearbyPublicTransportation, retrievedProperty.interestNearbyPublicTransportation)
        assertEquals(property1.interestNearbyPharmacy, retrievedProperty.interestNearbyPharmacy)
    }

    @Test
    fun testUpdateProperty() = runBlocking {
        val property = createProperty1()
        val insertedId = propertyDao.insert(property)

        val updatedProperty = property.copy(
            id = insertedId.toInt(),
            price = 120000,
            description = "Updated description"
        )
        propertyDao.update(updatedProperty)

        val retrievedProperty = propertyDao.getProperty(insertedId.toInt()).first()

        assertEquals(insertedId.toInt(), retrievedProperty.id)
        assertEquals(updatedProperty.type, retrievedProperty.type)
        assertEquals(updatedProperty.price, retrievedProperty.price)
        assertEquals(updatedProperty.address, retrievedProperty.address)
        assertEquals(updatedProperty.latitude, retrievedProperty.latitude)
        assertEquals(updatedProperty.longitude, retrievedProperty.longitude)
        assertEquals(updatedProperty.surface, retrievedProperty.surface)
        assertEquals(updatedProperty.room, retrievedProperty.room)
        assertEquals(updatedProperty.bedroom, retrievedProperty.bedroom)
        assertEquals(updatedProperty.description, retrievedProperty.description)
        assertEquals(updatedProperty.entryDate, retrievedProperty.entryDate)
        assertEquals(updatedProperty.soldDate, retrievedProperty.soldDate)
        assertEquals(updatedProperty.sold, retrievedProperty.sold)
        assertEquals(updatedProperty.agent, retrievedProperty.agent)
        assertEquals(updatedProperty.interestNearbySchool, retrievedProperty.interestNearbySchool)
        assertEquals(updatedProperty.interestNearbyShop, retrievedProperty.interestNearbyShop)
        assertEquals(updatedProperty.interestNearbyPark, retrievedProperty.interestNearbyPark)
        assertEquals(updatedProperty.interestNearbyRestaurant, retrievedProperty.interestNearbyRestaurant)
        assertEquals(updatedProperty.interestNearbyPublicTransportation, retrievedProperty.interestNearbyPublicTransportation)
        assertEquals(updatedProperty.interestNearbyPharmacy, retrievedProperty.interestNearbyPharmacy)
    }

    @Test
    fun testDeleteProperty() = runBlocking {
        val property = createProperty1()
        val insertedId = propertyDao.insert(property)

        val retrievedProperty = propertyDao.getProperty(insertedId.toInt()).first()
        assertEquals(insertedId.toInt(), retrievedProperty.id)

        propertyDao.delete(retrievedProperty)
        val properties = propertyDao.getAllProperty().first()
        assertEquals(0, properties.size)
    }

    @Test
    fun testGetPropertyAndPicture() = runBlocking {
        val property = createProperty1()
        val insertedId = propertyDao.insert(property)

        // Assuming PictureDao and Picture are correctly implemented and related to Property
        val picture = Picture(
            id = 0,
            propertyId = insertedId.toInt(),
            description = "Picture description",
            imageData = byteArrayOf(0x1, 0x2)
        )
        pictureDao.insert(picture)

        val propertyWithPicture = propertyDao.getPropertyAndPicture(insertedId.toInt()).first()

        assertEquals(insertedId.toInt(), propertyWithPicture?.property?.id)
        assertEquals(1, propertyWithPicture?.picture?.size)
        val retrievedPicture = propertyWithPicture?.picture?.get(0)
        assertEquals(picture.description, retrievedPicture?.description)
        assertEquals(picture.imageData.size, retrievedPicture?.imageData?.size)
    }

    @Test
    fun testGetAllPropertyAndPicture() = runBlocking {
        val property1 = createProperty1()
        val property2 = createProperty2()
        val insertedProperty1Id = propertyDao.insert(property1)
        val insertedProperty2Id = propertyDao.insert(property2)

        // Assuming PictureDao and Picture are correctly implemented and related to Property
        val picture1 = Picture(
            id = 0,
            propertyId = insertedProperty1Id.toInt(),
            description = "Picture description 1",
            imageData = byteArrayOf(0x1, 0x2)
        )
        val picture2 = Picture(
            id = 0,
            propertyId = insertedProperty2Id.toInt(),
            description = "Picture description 2",
            imageData = byteArrayOf(0x3, 0x4)
        )
        pictureDao.insert(picture1)
        pictureDao.insert(picture2)

        val propertiesWithPictures = propertyDao.getAllPropertyAndPicture().first()

        assertEquals(2, propertiesWithPictures.size)

        val retrievedPropertyWithPicture1 = propertiesWithPictures.first { it.property.id == insertedProperty1Id.toInt() }
        assertEquals(1, retrievedPropertyWithPicture1.picture.size)
        assertEquals(picture1.description, retrievedPropertyWithPicture1.picture[0].description)
        assertEquals(picture1.imageData.size, retrievedPropertyWithPicture1.picture[0].imageData.size)

        val retrievedPropertyWithPicture2 = propertiesWithPictures.first { it.property.id == insertedProperty2Id.toInt() }
        assertEquals(1, retrievedPropertyWithPicture2.picture.size)
        assertEquals(picture2.description, retrievedPropertyWithPicture2.picture[0].description)
        assertEquals(picture2.imageData.size, retrievedPropertyWithPicture2.picture[0].imageData.size)
    }

    @Test
    fun testInsertPicture() = runBlocking {
        val property = createProperty1()
        val insertedPropertyId = propertyDao.insert(property)

        val picture = Picture(
            propertyId = insertedPropertyId.toInt(),
            description = "Picture description",
            imageData = byteArrayOf(0x1, 0x2)
        )
        val insertedPictureId = pictureDao.insert(picture)

        val pictures = pictureDao.getAllPicture().first()
        assertEquals(1, pictures.size)
        val retrievedPicture = pictures[0]
        assertEquals(insertedPictureId.toInt(), retrievedPicture.id)
        assertEquals(picture.propertyId, retrievedPicture.propertyId)
        assertEquals(picture.description, retrievedPicture.description)
        assertEquals(picture.imageData.size, retrievedPicture.imageData.size)
    }

    @Test
    fun testUpdatePicture() = runBlocking {
        val property = createProperty1()
        val insertedPropertyId = propertyDao.insert(property)

        val picture = Picture(
            propertyId = insertedPropertyId.toInt(),
            description = "Picture description",
            imageData = byteArrayOf(0x1, 0x2)
        )
        val insertedPictureId = pictureDao.insert(picture)

        val updatedPicture = Picture(
            id = insertedPictureId.toInt(),
            propertyId = insertedPropertyId.toInt(),
            description = "Updated picture description",
            imageData = byteArrayOf(0x3, 0x4)
        )
        pictureDao.update(updatedPicture)

        val retrievedPicture = pictureDao.getPicture(insertedPictureId.toInt()).first()
        assertEquals(updatedPicture.id, retrievedPicture?.id)
        assertEquals(updatedPicture.propertyId, retrievedPicture?.propertyId)
        assertEquals(updatedPicture.description, retrievedPicture?.description)
        assertEquals(updatedPicture.imageData.size, retrievedPicture?.imageData?.size)
    }

    @Test
    fun testDeletePicture() = runBlocking {
        val property = createProperty1()
        val insertedPropertyId = propertyDao.insert(property)

        val picture = Picture(
            propertyId = insertedPropertyId.toInt(),
            description = "Picture description",
            imageData = byteArrayOf(0x1, 0x2)
        )
        val insertedPictureId = pictureDao.insert(picture)

        val pictureWithId = pictureDao.getPicture(insertedPictureId.toInt()).first()

        pictureWithId?.let {
            pictureDao.delete(it)
        }

        val retrievedPicture = pictureDao.getPicture(insertedPictureId.toInt()).first()
        assertNull(retrievedPicture)
    }

    @Test
    fun testGetPicture() = runBlocking {
        val property = createProperty1()
        val insertedPropertyId = propertyDao.insert(property)

        val picture = Picture(
            propertyId = insertedPropertyId.toInt(),
            description = "Picture description",
            imageData = byteArrayOf(0x1, 0x2)
        )
        val insertedPictureId = pictureDao.insert(picture)

        val retrievedPicture = pictureDao.getPicture(insertedPictureId.toInt()).first()
        assertEquals(insertedPictureId.toInt(), retrievedPicture?.id)
        assertEquals(picture.propertyId, retrievedPicture?.propertyId)
        assertEquals(picture.description, retrievedPicture?.description)
        assertEquals(picture.imageData.size, retrievedPicture?.imageData?.size)
    }

    @Test
    fun testGetPicturesByProperty() = runBlocking {
        val property = createProperty1()
        val insertedPropertyId = propertyDao.insert(property)

        val property2 = createProperty2()
        val insertedProperty2Id= propertyDao.insert(property2)

        val pictureOfProperty2 = Picture(
            propertyId = insertedProperty2Id.toInt(),
            description = "Picture pictureOfProperty2",
            imageData = byteArrayOf(0x3, 0x4)
        )
        pictureDao.insert(pictureOfProperty2)

        val picture1 = Picture(
            propertyId = insertedPropertyId.toInt(),
            description = "Picture description 1",
            imageData = byteArrayOf(0x1, 0x2)
        )
        val picture2 = Picture(
            propertyId = insertedPropertyId.toInt(),
            description = "Picture description 2",
            imageData = byteArrayOf(0x3, 0x4)
        )
        pictureDao.insert(picture1)
        pictureDao.insert(picture2)

        val pictures = pictureDao.getPicturesByProperty(insertedPropertyId.toInt()).first()
        assertEquals(2, pictures?.size)
        assertEquals(picture1.description, pictures?.get(0)?.description)
        assertEquals(picture2.description, pictures?.get(1)?.description)
    }



    @After
    fun tearDown(){
        database.close()
    }
}