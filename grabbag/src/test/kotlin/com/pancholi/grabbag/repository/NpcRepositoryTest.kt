package com.pancholi.grabbag.repository

import com.pancholi.grabbag.database.GrabBagDatabase
import com.pancholi.grabbag.database.dao.NpcDao
import com.pancholi.grabbag.database.entity.NpcEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class NpcRepositoryTest {

    private val npcDao = mock<NpcDao>()
    private val database = mock<GrabBagDatabase> {
        on { npcDao() } doReturn npcDao
    }
    private val repository = NpcRepository(
        database = database
    )

    @Test
    fun `test inserting single npc to database`() = runTest {
        val npc = mock<NpcEntity>()

        repository.insertNpc(npc)

        verify(npcDao).insert(npc)
    }

    @Test
    fun `test inserting multiple npcs to database`() = runTest {
        val npcs = listOf(
            mock<NpcEntity>(),
            mock<NpcEntity>()
        )

        repository.insertAllNpcs(npcs)

        verify(npcDao).insertAll(npcs)
    }
}