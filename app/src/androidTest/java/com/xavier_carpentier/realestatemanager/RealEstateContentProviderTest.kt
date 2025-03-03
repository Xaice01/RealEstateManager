package com.xavier_carpentier.realestatemanager

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.xavier_carpentier.realestatemanager.data.content_provider.RealEstateContentProvider
import com.xavier_carpentier.realestatemanager.datasource.database.DatabaseRealEstateManager
import com.xavier_carpentier.realestatemanager.datasource.property.Property
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class RealEstateContentProviderInstrumentedTest {

    private lateinit var context: android.content.Context
    private lateinit var database: DatabaseRealEstateManager
    private lateinit var contentResolver: ContentResolver

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        context.deleteDatabase("RealEstateManager_database")
        database = Room.databaseBuilder(
            context,
            DatabaseRealEstateManager::class.java,
            "RealEstateManager_database"
        ).fallbackToDestructiveMigration().build()
        contentResolver = context.contentResolver
    }

    @After
    fun tearDown() {
        context.deleteDatabase("RealEstateManager_database")
    }

    @Test
    fun testQueryEmptyProperty() {
        // Construire l'URI pour cette propriété spécifique
        val propertyUri: Uri = Uri.withAppendedPath(RealEstateContentProvider.PROPERTY_URI,
            5.toLong().toString()
        )
        val cursor: Cursor? = contentResolver.query(
            propertyUri,
            null, null, null, null
        )
        assertNotNull(cursor)
        cursor?.use {
            assertEquals("Aucune propriété ne doit être présente", 0, it.count)
        }
    }

    @Test
    fun testQueryPropertyList() {
        runBlocking {
            val property = Property(
                id = 0,
                type = "House",
                price = 1000000,
                address = "123 Main St",
                latitude = 10.0,
                longitude = 20.0,
                surface = 120,
                room = 5,
                bedroom = 3,
                description = "A lovely house",
                entryDate = Calendar.getInstance(),
                soldDate = null,
                sold = false,
                agent = "AgentX",
                interestNearbySchool = true,
                interestNearbyShop = false,
                interestNearbyPark = true,
                interestNearbyRestaurant = false,
                interestNearbyPublicTransportation = true,
                interestNearbyPharmacy = false
            )
            val insertedId = database.propertyDao().insert(property)
            // On interroge le ContentProvider après l'insertion
            val cursor: Cursor? = contentResolver.query(
                RealEstateContentProvider.PROPERTY_URI,
                null, null, null, null
            )
            assertNotNull(cursor)
            cursor?.use {
                assertEquals("Une propriété doit être présente", 1, it.count)
                if (it.moveToFirst()) {
                    val idIndex = it.getColumnIndex("id")
                    assertEquals(insertedId.toInt(), it.getInt(idIndex))
                }
            }
        }
    }

    @Test
    fun testQueryPropertyById() {
        runBlocking {
            val property = Property(
                id = 0,
                type = "Apartment",
                price = 500000,
                address = "456 Elm St",
                latitude = 30.0,
                longitude = 40.0,
                surface = 80,
                room = 4,
                bedroom = 2,
                description = "A modern apartment",
                entryDate = Calendar.getInstance(),
                soldDate = null,
                sold = false,
                agent = "AgentY",
                interestNearbySchool = false,
                interestNearbyShop = true,
                interestNearbyPark = false,
                interestNearbyRestaurant = true,
                interestNearbyPublicTransportation = true,
                interestNearbyPharmacy = true
            )
            val insertedId = database.propertyDao().insert(property)
            // Construire l'URI pour cette propriété spécifique
            val propertyUri: Uri = Uri.withAppendedPath(RealEstateContentProvider.PROPERTY_URI, insertedId.toString())
            val cursor: Cursor? = contentResolver.query(
                propertyUri,
                null, null, null, null
            )
            assertNotNull(cursor)
            cursor?.use {
                assertEquals("Une seule propriété doit être retournée", 1, it.count)
                if (it.moveToFirst()) {
                    val idIndex = it.getColumnIndex("id")
                    assertEquals(insertedId.toInt(), it.getInt(idIndex))
                    val typeIndex = it.getColumnIndex("type")
                    assertEquals("Apartment", it.getString(typeIndex))
                }
            }
        }
    }
}