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

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

import com.sefford.brender.interfaces.AdapterData;
import com.sefford.brender.interfaces.Renderable;

import java.util.List;

/**
 * Merge of AdapterData and Cursor interfaces. As it uses POJOs to work as with the rest of RendererAdapters,
 * does not return column names or values from such columns. In order to add this functionality, a
 * particular implementation will be required. By default, non-null implementations will be returned.
 * <p/>
 * The CursorAdapterData can go backwards and forwards at no cost, and will not throw an exception
 * when its limits are reached. Will return first and last positions continously in those cases.
 * <p/>
 * Additionally this CursorAdapterData always reports open.
 *
 * @author Saul Diaz <sefford@gmail.com>
 * @see Cursor
 * @see AdapterData
 */
public abstract class CursorAdapterData extends FilterableAdapterData implements Cursor {
    public static final String[] NULL_COLUMN_NAMES = new String[0];
    public static final byte[] NULL_BLOB = new byte[0];
    /**
     * Current Cursor position
     */
    int currentPos = 0;

    /**
     * Creates a new instance of CursorAdapterData
     *
     * @param master External data
     */
    public CursorAdapterData(List<Renderable> master) {
        super(master);
        this.currentPos = 0;
    }

    @Override
    public int getCount() {
        return size();
    }

    @Override
    public int getPosition() {
        return currentPos;
    }

    @Override
    public boolean move(int offset) {
        currentPos = Math.max(0, Math.min(size() - 1, currentPos + offset));
        return true;
    }

    @Override
    public boolean moveToPosition(int position) {
        currentPos = Math.max(0, Math.min(size() - 1, position));
        return true;
    }

    @Override
    public boolean moveToFirst() {
        currentPos = 0;
        return true;
    }

    @Override
    public boolean moveToLast() {
        currentPos = size() - 1;
        return true;
    }

    @Override
    public boolean moveToNext() {
        currentPos = Math.min(master.size() - 1, currentPos + 1);
        return true;
    }

    @Override
    public boolean moveToPrevious() {
        currentPos = Math.max(0, currentPos - 1);
        return true;
    }

    @Override
    public boolean isFirst() {
        return currentPos == 0;
    }

    @Override
    public boolean isLast() {
        return currentPos == size() - 1;
    }

    @Override
    public boolean isBeforeFirst() {
        return currentPos < 0;
    }

    @Override
    public boolean isAfterLast() {
        return currentPos > size() - 1;
    }

    @Override
    public int getColumnIndex(String columnName) {
        return 0;
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return "";
    }

    @Override
    public String[] getColumnNames() {
        return NULL_COLUMN_NAMES;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        return NULL_BLOB;
    }

    @Override
    public String getString(int columnIndex) {
        return "";
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {

    }

    @Override
    public short getShort(int columnIndex) {
        return 0;
    }

    @Override
    public int getInt(int columnIndex) {
        return 0;
    }

    @Override
    public long getLong(int columnIndex) {
        return 0;
    }

    @Override
    public float getFloat(int columnIndex) {
        return 0;
    }

    @Override
    public double getDouble(int columnIndex) {
        return 0;
    }

    @Override
    public int getType(int columnIndex) {
        return 0;
    }

    @Override
    public boolean isNull(int columnIndex) {
        return false;
    }

    @Override
    public void deactivate() {

    }

    @Override
    public boolean requery() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {

    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {

    }

    @Override
    public Uri getNotificationUri() {
        return Uri.EMPTY;
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        return false;
    }

    @Override
    public Bundle getExtras() {
        return Bundle.EMPTY;
    }

    @Override
    public Bundle respond(Bundle extras) {
        return Bundle.EMPTY;
    }
}
