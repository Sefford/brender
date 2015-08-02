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
package com.sefford.brender.data;

import android.net.Uri;
import android.os.Bundle;

import com.sefford.brender.interfaces.Renderable;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by sefford on 2/08/15.
 */
public class CursorAdapterDataTest {

    static final int EXPECTED_FIRST_POS = 0;
    static final int EXPECTED_MIDDLE_POS = 1;
    static final int EXPECTED_FINAL_POS = 2;
    public static final String DEFAULT_STRING = "";

    CursorAdapterData data;

    List<Renderable> renderables;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        renderables = new ArrayList<>();
        final Renderable renderable = new Renderable() {
            @Override
            public int getRenderableId() {
                return 0;
            }
        };
        renderables.add(renderable);
        renderables.add(renderable);
        renderables.add(renderable);
        data = new CursorAdapterData(renderables) {
            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public void notifyDataSetChanged() {

            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }
        };
    }

    @Test
    public void testGetCount() throws Exception {
        assertEquals(renderables.size(), data.getCount());
    }

    @Test
    public void testGetPosition() throws Exception {
        data.currentPos = EXPECTED_MIDDLE_POS;

        assertEquals(EXPECTED_MIDDLE_POS, data.getPosition());
    }

    @Test
    public void testMove() throws Exception {
        data.move(1);

        assertEquals(EXPECTED_MIDDLE_POS, data.currentPos);
    }

    @Test
    public void testMoveToPosition() throws Exception {
        data.moveToPosition(EXPECTED_MIDDLE_POS);

        assertEquals(EXPECTED_MIDDLE_POS, data.currentPos);
    }

    @Test
    public void testMoveToFirst() throws Exception {
        data.currentPos = EXPECTED_FINAL_POS;

        data.moveToFirst();

        assertEquals(EXPECTED_FIRST_POS, data.currentPos);
    }

    @Test
    public void testMoveToLast() throws Exception {
        data.moveToLast();

        assertEquals(EXPECTED_FINAL_POS, data.currentPos);
    }

    @Test
    public void testMoveToNext() throws Exception {
        data.moveToNext();

        assertEquals(EXPECTED_MIDDLE_POS, data.currentPos);
    }

    @Test
    public void testMoveToPrevious() throws Exception {
        data.currentPos = EXPECTED_FINAL_POS;

        data.moveToPrevious();

        assertEquals(EXPECTED_MIDDLE_POS, data.currentPos);
    }

    @Test
    public void testIsFirstNotFirst() throws Exception {
        data.currentPos = EXPECTED_FINAL_POS;

        assertFalse(data.isFirst());
    }

    @Test
    public void testIsFirst() throws Exception {
        assertTrue(data.isFirst());
    }

    @Test
    public void testIsLast() throws Exception {
        data.currentPos = EXPECTED_FINAL_POS;

        assertTrue(data.isLast());
    }

    @Test
    public void testIsLastNotLast() throws Exception {
        assertFalse(data.isLast());
    }

    @Test
    public void testIsBeforeFirst() throws Exception {
        assertFalse(data.isBeforeFirst());
    }

    @Test
    public void testIsAfterLast() throws Exception {
        assertFalse(data.isAfterLast());
    }

    @Test
    public void testGetColumnIndex() throws Exception {
        assertEquals(0, data.getColumnIndex(""));
    }

    @Test
    public void testGetColumnIndexOrThrow() throws Exception {
        assertEquals(0, data.getColumnIndexOrThrow(""));

    }

    @Test
    public void testGetColumnName() throws Exception {
        assertEquals(DEFAULT_STRING, data.getColumnName(0));
    }

    @Test
    public void testGetColumnNames() throws Exception {
        assertArrayEquals(CursorAdapterData.NULL_COLUMN_NAMES, data.getColumnNames());
    }

    @Test
    public void testGetColumnCount() throws Exception {
        assertEquals(0, data.getColumnCount());
    }

    @Test
    public void testGetBlob() throws Exception {
        assertArrayEquals(CursorAdapterData.NULL_BLOB, data.getBlob(0));
    }

    @Test
    public void testGetString() throws Exception {
        assertEquals(DEFAULT_STRING, data.getString(0));
    }

    @Test
    public void testGetShort() throws Exception {
        assertEquals(0, data.getShort(0));
    }

    @Test
    public void testGetInt() throws Exception {
        assertEquals(0, data.getInt(0));
    }

    @Test
    public void testGetLong() throws Exception {
        assertEquals(0, data.getLong(0));
    }

    @Test
    public void testGetFloat() throws Exception {
        assertEquals(0f, data.getFloat(0), 0);
    }

    @Test
    public void testGetDouble() throws Exception {
        assertEquals(0.0, data.getDouble(0), 0);
    }

    @Test
    public void testGetType() throws Exception {
        assertEquals(0, data.getType(0));
    }

    @Test
    public void testIsNull() throws Exception {
        assertFalse(data.isNull(0));
    }

    @Test
    public void testRequery() throws Exception {
        assertFalse(data.requery());
    }

    @Test
    public void testIsClosed() throws Exception {
        assertFalse(data.isClosed());
    }

    @Test
    public void testGetNotificationUri() throws Exception {
        assertEquals(Uri.EMPTY, data.getNotificationUri());
    }

    @Test
    public void testGetWantsAllOnMoveCalls() throws Exception {
        assertFalse(data.getWantsAllOnMoveCalls());
    }

    @Test
    public void testGetExtras() throws Exception {
        assertEquals(Bundle.EMPTY, data.getExtras());
    }

    @Test
    public void testRespond() throws Exception {
        assertEquals(Bundle.EMPTY, data.respond(Bundle.EMPTY));
    }
}