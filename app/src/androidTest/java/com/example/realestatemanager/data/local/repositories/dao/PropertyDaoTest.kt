package com.example.realestatemanager.data.local.repositories.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.realestatemanager.data.local.dao.PropertyDao
import com.example.realestatemanager.data.local.db.PropertyDatabase
import com.example.realestatemanager.data.local.model.Property
import com.example.realestatemanager.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PropertyDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: PropertyDatabase
    private lateinit var dao: PropertyDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PropertyDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.propertyDao
    }


    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertProperty_shouldInsertPropertyIntoDatabase() = runTest {
        dao.insertProperty(estate)
        dao.getProperties().test {
            val emission = awaitItem()
            assertThat(emission).contains(estate)
        }
    }

    @Test
    fun getPropertyById_ShouldReturnTheCorrectProperty() = runTest {
        dao.insertProperty(estate)
        val result = dao.getPropertyById(1).getOrAwaitValue()
        assertThat(result === estate)
    }

    @Test
    fun insertPropertyWithTheSameID_ShouldUpdateThisProperty() = runTest {
        val estateDuplicate = estate
        estateDuplicate.saleDate = "09/11/2022"
        dao.insertProperty(estate)
        dao.insertProperty(estateDuplicate)
        dao.getProperties().test {
            val emission = awaitItem()
            assertThat(emission).contains(estateDuplicate)
            assertThat(emission.size).isEqualTo(1)
        }
    }




    private val estate =
        Property(
            id = 1,
            type = "Duplex",
            description = "Centre Ville - 4 place du Sanitat appartement T1 meublé se composant d'une entrée, pièce de vie avec cuisine a/e, une salle d'eau et wc séparé. Forfait d'électricité de 60 euros en sus Disponible immédiatement\n",
            price = 10500000,
            squareMeter = 100f,
            location = listOf(
                "227 Swan Landing Lane",
                "Great Neck",
                "Ny",
                "11024",
                "United States"
            ),
            numberOfRooms = 8,
            numberOfBedRooms = 5,
            numberOfBathRooms = 3,
            pictures = listOf(
                "https://images.pexels.com/photos/323780/pexels-photo-323780.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                "https://images.pexels.com/photos/101808/pexels-photo-101808.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                "https://images.pexels.com/photos/2121121/pexels-photo-2121121.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                "https://images.pexels.com/photos/2635038/pexels-photo-2635038.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                "https://images.pexels.com/photos/1974596/pexels-photo-1974596.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
            ),
            poi = listOf("Sea", "School", "Fitness Park", "Garden", "Subway"),
            status = true,
            entryDate = "30/10/2022",
            saleDate = null,
            estateManagerName = "tristan"
        )


}