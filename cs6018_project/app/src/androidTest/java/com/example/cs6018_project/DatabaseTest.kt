package com.example.cs6018_project

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cs6018_project.room.BoardDatabase
import com.example.cs6018_project.room.Item
import com.example.cs6018_project.room.ItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var dao: ItemDao
    private lateinit var db: BoardDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BoardDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.itemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testAddAll() {
        runBlocking {
            val newDrawing = Item(name="Drawing1", image = ByteArray(10))
            val lifecycleOwner = TestLifecycleOwner()
            var count = 0
            lifecycleOwner.run {
                withContext(Dispatchers.Main) {
                    val _allDrawing = MutableLiveData<List<Item>>(dao.getAll())
                    val allDrawing: LiveData<List<Item>> = _allDrawing
                    allDrawing.observe(lifecycleOwner) {
                        if (count == 1) {
                            Assert.assertTrue(it.contains(newDrawing))
                        }
                    }
                    dao.insertAll(newDrawing)
                    count += 1
                }
            }
        }
    }

}