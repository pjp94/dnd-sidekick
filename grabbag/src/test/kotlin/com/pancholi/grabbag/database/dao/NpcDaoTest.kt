package com.pancholi.grabbag.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.pancholi.grabbag.CoroutineTestRule
import com.pancholi.grabbag.database.GrabBagDatabase
import com.pancholi.grabbag.database.entity.NpcEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class NpcDaoTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var database: GrabBagDatabase
    private lateinit var npcDao: NpcDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, GrabBagDatabase::class.java)
            .build()
        npcDao = database.npcDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `test inserting single npc`() = runTest {
        val npc = createNpc(1)

        npcDao.insert(npc)

        npcDao.getAll().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals(npc, result.first())
            cancel()
        }
    }

    @Test
    fun `test inserting same npc replaces previous one`() = runTest {
        val npc = createNpc(1)

        npcDao.apply {
            insert(npc)
            insert(npc)
        }

        npcDao.getAll().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals(npc, result.first())
            cancel()
        }
    }

    @Test
    fun `test inserting two different npcs`() = runTest {
        val npcs = listOf(createNpc(1), createNpc(2))

        npcDao.insertAll(npcs)

        npcDao.getAll().test {
            val result = awaitItem()
            assertEquals(2, result.size)
            assertEquals(npcs, result)
            cancel()
        }
    }

    @Test
    fun `test updating npc`() = runTest {
        val npc = createNpc(1)
        npcDao.insert(npc)

        val updatedNpc = npc.copy(name = "updated-name")
        npcDao.update(updatedNpc)

        npcDao.getAll().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals(updatedNpc, result.first())
            cancel()
        }
    }

    @Test
    fun `test deleting npc`() = runTest {
        val npc = createNpc(1)
        npcDao.insert(npc)

        npcDao.delete(npc)

        npcDao.getAll().test {
            val result = awaitItem()
            assertTrue(result.isEmpty())
            cancel()
        }
    }

    @Test
    fun `test getting empty list of npcs`() = runTest {
        npcDao.getAll().test {
            val result = awaitItem()
            assertTrue(result.isEmpty())
            cancel()
        }
    }

    private fun createNpc(id: Int): NpcEntity {
        return NpcEntity(
            id = id,
            name = "test-name",
            race = "test-race",
            gender = "test-gender",
            clss = "test-class",
            type = "test-type",
            description = "test-description"
        )
    }
}