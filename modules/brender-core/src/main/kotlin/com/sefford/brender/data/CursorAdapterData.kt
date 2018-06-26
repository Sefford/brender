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

import android.content.ContentResolver
import android.database.CharArrayBuffer
import android.database.ContentObserver
import android.database.Cursor
import android.database.DataSetObserver
import android.net.Uri
import android.os.Bundle
import com.sefford.brender.components.AdapterData
import com.sefford.brender.components.Renderable

/**
 * Merge of AdapterData and Cursor interfaces. As it uses POJOs to work as with the rest of RendererAdapters,
 * does not return column names or values from such columns. In order to add this functionality, a
 * particular implementation will be required. By default, non-null implementations will be returned.
 *
 *
 * The CursorAdapterData can go backwards and forwards at no cost, and will not throw an exception
 * when its limits are reached. Will return first and last positions continously in those cases.
 *
 *
 * Additionally this CursorAdapterData always reports open.
 *
 * @author Saul Diaz <sefford></sefford>@gmail.com>
 * @see Cursor
 *
 * @see AdapterData
 */
abstract class CursorAdapterData
/**
 * Creates a new instance of CursorAdapterData
 *
 * @param master External data
 */
(master: List<Renderable>) : FilterableAdapterData(master), Cursor {
    /**
     * Current Cursor position
     */
    internal var currentPos = 0

    init {
        this.currentPos = 0
    }

    override fun getCount(): Int {
        return size()
    }

    override fun getPosition(): Int {
        return currentPos
    }

    override fun move(offset: Int): Boolean {
        currentPos = Math.max(0, Math.min(size() - 1, currentPos + offset))
        return true
    }

    override fun moveToPosition(position: Int): Boolean {
        currentPos = Math.max(0, Math.min(size() - 1, position))
        return true
    }

    override fun moveToFirst(): Boolean {
        currentPos = 0
        return true
    }

    override fun moveToLast(): Boolean {
        currentPos = size() - 1
        return true
    }

    override fun moveToNext(): Boolean {
        currentPos = Math.min(master.size - 1, currentPos + 1)
        return true
    }

    override fun moveToPrevious(): Boolean {
        currentPos = Math.max(0, currentPos - 1)
        return true
    }

    override fun isFirst(): Boolean {
        return currentPos == 0
    }

    override fun isLast(): Boolean {
        return currentPos == size() - 1
    }

    override fun isBeforeFirst(): Boolean {
        return currentPos < 0
    }

    override fun isAfterLast(): Boolean {
        return currentPos > size() - 1
    }

    override fun getColumnIndex(columnName: String): Int {
        return 0
    }

    @Throws(IllegalArgumentException::class)
    override fun getColumnIndexOrThrow(columnName: String): Int {
        return 0
    }

    override fun getColumnName(columnIndex: Int): String {
        return ""
    }

    override fun getColumnNames(): Array<String> {
        return NULL_COLUMN_NAMES
    }

    override fun getColumnCount(): Int {
        return 0
    }

    override fun getBlob(columnIndex: Int): ByteArray {
        return NULL_BLOB
    }

    override fun getString(columnIndex: Int): String {
        return ""
    }

    override fun copyStringToBuffer(columnIndex: Int, buffer: CharArrayBuffer) {

    }

    override fun getShort(columnIndex: Int): Short {
        return 0
    }

    override fun getInt(columnIndex: Int): Int {
        return 0
    }

    override fun getLong(columnIndex: Int): Long {
        return 0
    }

    override fun getFloat(columnIndex: Int): Float {
        return 0f
    }

    override fun getDouble(columnIndex: Int): Double {
        return 0.0
    }

    override fun getType(columnIndex: Int): Int {
        return 0
    }

    override fun isNull(columnIndex: Int): Boolean {
        return false
    }

    override fun deactivate() {

    }

    override fun requery(): Boolean {
        return false
    }

    override fun close() {

    }

    override fun isClosed(): Boolean {
        return false
    }

    override fun registerContentObserver(observer: ContentObserver) {

    }

    override fun unregisterContentObserver(observer: ContentObserver) {

    }

    override fun registerDataSetObserver(observer: DataSetObserver) {

    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {

    }

    override fun setNotificationUri(cr: ContentResolver, uri: Uri) {

    }

    override fun getNotificationUri(): Uri {
        return Uri.EMPTY
    }

    override fun getWantsAllOnMoveCalls(): Boolean {
        return false
    }

    override fun getExtras(): Bundle {
        return Bundle.EMPTY
    }

    override fun respond(extras: Bundle): Bundle {
        return Bundle.EMPTY
    }

    companion object {
        val NULL_COLUMN_NAMES = emptyArray<String>()
        val NULL_BLOB = ByteArray(0)
    }
}
