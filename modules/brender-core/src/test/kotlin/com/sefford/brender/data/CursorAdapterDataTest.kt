/*
 * Copyright (C) 2015 Saúl Díaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sefford.brender.data

import android.net.Uri
import android.os.Bundle
import android.widget.Filter
import com.sefford.brender.components.Renderable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations.initMocks
import org.robolectric.RobolectricTestRunner
import java.util.*

/**
 * Created by sefford on 2/08/15.
 */
@RunWith(RobolectricTestRunner::class)
class CursorAdapterDataTest {

    lateinit var data: CursorAdapterData

    lateinit var renderables: MutableList<Renderable>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)

        renderables = ArrayList()
        val renderable = object : Renderable {
            override val renderableId: Int
                get() = 0
        }
        renderables.add(renderable)
        renderables.add(renderable)
        renderables.add(renderable)
        data = object : CursorAdapterData(renderables) {

            override val viewTypeCount: Int
                get() = 0

            override fun setExtras(extras: Bundle) {

            }

            override fun notifyDataSetChanged() {

            }

            override fun performFiltering(constraint: CharSequence): Filter.FilterResults? {
                return null
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun testGetCount() {
        assertEquals(renderables.size.toLong(), data.count.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetPosition() {
        data.currentPos = EXPECTED_MIDDLE_POS

        assertEquals(EXPECTED_MIDDLE_POS.toLong(), data.position.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testMove() {
        data.move(1)

        assertEquals(EXPECTED_MIDDLE_POS.toLong(), data.currentPos.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testMoveToPosition() {
        data.moveToPosition(EXPECTED_MIDDLE_POS)

        assertEquals(EXPECTED_MIDDLE_POS.toLong(), data.currentPos.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testMoveToFirst() {
        data.currentPos = EXPECTED_FINAL_POS

        data.moveToFirst()

        assertEquals(EXPECTED_FIRST_POS.toLong(), data.currentPos.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testMoveToLast() {
        data.moveToLast()

        assertEquals(EXPECTED_FINAL_POS.toLong(), data.currentPos.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testMoveToNext() {
        data.moveToNext()

        assertEquals(EXPECTED_MIDDLE_POS.toLong(), data.currentPos.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testMoveToPrevious() {
        data.currentPos = EXPECTED_FINAL_POS

        data.moveToPrevious()

        assertEquals(EXPECTED_MIDDLE_POS.toLong(), data.currentPos.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testIsFirstNotFirst() {
        data.currentPos = EXPECTED_FINAL_POS

        assertFalse(data.isFirst)
    }

    @Test
    @Throws(Exception::class)
    fun testIsFirst() {
        assertTrue(data.isFirst)
    }

    @Test
    @Throws(Exception::class)
    fun testIsLast() {
        data.currentPos = EXPECTED_FINAL_POS

        assertTrue(data.isLast)
    }

    @Test
    @Throws(Exception::class)
    fun testIsLastNotLast() {
        assertFalse(data.isLast)
    }

    @Test
    @Throws(Exception::class)
    fun testIsBeforeFirst() {
        assertFalse(data.isBeforeFirst)
    }

    @Test
    @Throws(Exception::class)
    fun testIsAfterLast() {
        assertFalse(data.isAfterLast)
    }

    @Test
    @Throws(Exception::class)
    fun testGetColumnIndex() {
        assertEquals(0, data.getColumnIndex("").toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetColumnIndexOrThrow() {
        assertEquals(0, data.getColumnIndexOrThrow("").toLong())

    }

    @Test
    @Throws(Exception::class)
    fun testGetColumnName() {
        assertEquals(DEFAULT_STRING, data.getColumnName(0))
    }

    @Test
    @Throws(Exception::class)
    fun testGetColumnNames() {
        assertArrayEquals(CursorAdapterData.NULL_COLUMN_NAMES, data.columnNames)
    }

    @Test
    @Throws(Exception::class)
    fun testGetColumnCount() {
        assertEquals(0, data.columnCount.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetBlob() {
        assertArrayEquals(CursorAdapterData.NULL_BLOB, data.getBlob(0))
    }

    @Test
    @Throws(Exception::class)
    fun testGetString() {
        assertEquals(DEFAULT_STRING, data.getString(0))
    }

    @Test
    @Throws(Exception::class)
    fun testGetShort() {
        assertEquals(0, data.getShort(0).toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetInt() {
        assertEquals(0, data.getInt(0).toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testGetLong() {
        assertEquals(0, data.getLong(0))
    }

    @Test
    @Throws(Exception::class)
    fun testGetFloat() {
        assertEquals(0f, data.getFloat(0), 0f)
    }

    @Test
    @Throws(Exception::class)
    fun testGetDouble() {
        assertEquals(0.0, data.getDouble(0), 0.0)
    }

    @Test
    @Throws(Exception::class)
    fun testGetType() {
        assertEquals(0, data.getType(0).toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testIsNull() {
        assertFalse(data.isNull(0))
    }

    @Test
    @Throws(Exception::class)
    fun testRequery() {
        assertFalse(data.requery())
    }

    @Test
    @Throws(Exception::class)
    fun testIsClosed() {
        assertFalse(data.isClosed)
    }

    @Test
    @Throws(Exception::class)
    fun testGetNotificationUri() {
        assertEquals(Uri.EMPTY, data.notificationUri)
    }

    @Test
    @Throws(Exception::class)
    fun testGetWantsAllOnMoveCalls() {
        assertFalse(data.wantsAllOnMoveCalls)
    }

    @Test
    @Throws(Exception::class)
    fun testGetExtras() {
        assertEquals(Bundle.EMPTY, data.extras)
    }

    @Test
    @Throws(Exception::class)
    fun testRespond() {
        assertEquals(Bundle.EMPTY, data.respond(Bundle.EMPTY))
    }

    companion object {

        internal val EXPECTED_FIRST_POS = 0
        internal val EXPECTED_MIDDLE_POS = 1
        internal val EXPECTED_FINAL_POS = 2
        val DEFAULT_STRING = ""
    }
}
