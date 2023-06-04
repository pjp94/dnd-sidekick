package com.pancholi.grabbag.database.dao

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.pancholi.grabbag.CoroutineTestRule
import com.pancholi.grabbag.database.GrabBagDatabase
import com.pancholi.grabbag.database.entity.NpcEntity
import com.pancholi.grabbag.database.entity.ShopEntity
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ShopDaoTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var database: GrabBagDatabase
    private lateinit var shopDao: ShopDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, GrabBagDatabase::class.java)
            .build()
        shopDao = database.shopDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `test inserting single shop`() = runTest {
        val shop = createShop(1)

        shopDao.insert(shop)

        shopDao.getAll().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals(shop, result.first())
            cancel()
        }
    }

    @Test
    fun `test inserting same shop replaces previous one`() = runTest {
        val shop = createShop(1)

        shopDao.apply {
            insert(shop)
            insert(shop)
        }

        shopDao.getAll().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals(shop, result.first())
            cancel()
        }
    }

    @Test
    fun `test inserting two different shops`() = runTest {
        val shops = listOf(createShop(1), createShop(2))

        shopDao.insertAll(shops)

        shopDao.getAll().test {
            val result = awaitItem()
            assertEquals(2, result.size)
            assertEquals(shops, result)
            cancel()
        }
    }

    @Test
    fun `test updating shop`() = runTest {
        val shop = createShop(1)
        shopDao.insert(shop)

        val updatedshop = shop.copy(name = "updated-name")
        shopDao.update(updatedshop)

        shopDao.getAll().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals(updatedshop, result.first())
            cancel()
        }
    }

    @Test
    fun `test deleting shop`() = runTest {
        val shop = createShop(1)
        shopDao.insert(shop)

        shopDao.delete(shop)

        shopDao.getAll().test {
            val result = awaitItem()
            TestCase.assertTrue(result.isEmpty())
            cancel()
        }
    }

    @Test
    fun `test getting empty list of shops`() = runTest {
        shopDao.getAll().test {
            val result = awaitItem()
            TestCase.assertTrue(result.isEmpty())
            cancel()
        }
    }

    @Test(expected = SQLiteConstraintException::class)
    fun `test error when inserting shop with npc that isn't in npc table`() = runTest {
        val shop = createShop(1).copy(npcId = 1)

        shopDao.insert(shop)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun `test error when updating shop with npc that isn't in npc table`() = runTest {
        val shop = createShop(1)
        shopDao.insert(shop)

        val updatedShop = shop.copy(npcId = 1)
        shopDao.update(updatedShop)
    }

    @Test
    fun `test inserting shop with npc that's present in npc table`() = runTest {
        val npc = NpcEntity(
            name = "test-name",
            race = "test-race",
            gender = "test-gender",
            clss = "test-class",
            profession = "test-profession"
        )
        val npcDao = database.npcDao()
        npcDao.insert(npc)

        npcDao.getAll().test {
            val id = awaitItem().first().id
            val shop = createShop(1).copy(npcId = id)

            shopDao.insert(shop)

            shopDao.getAll().test {
                val result = awaitItem()
                assertEquals(id, result.first().npcId)
                cancel()
            }
        }
    }

    @Test
    fun `test updating shop with npc that's present in npc table`() = runTest {
        val shop = createShop(1)
        shopDao.insert(shop)

        val npc = NpcEntity(
            name = "test-name",
            race = "test-race",
            gender = "test-gender",
            clss = "test-class",
            profession = "test-profession"
        )
        val npcDao = database.npcDao()
        npcDao.insert(npc)

        npcDao.getAll().test {
            val id = awaitItem().first().id
            val updatedShop = shop.copy(npcId = id)

            shopDao.insert(updatedShop)

            shopDao.getAll().test {
                val result = awaitItem()
                assertEquals(id, result.first().npcId)
                cancel()
            }
        }
    }

    private fun createShop(id: Int): ShopEntity {
        return ShopEntity(
            id = id,
            name = "test-name",
            type = "test-type"
        )
    }
}