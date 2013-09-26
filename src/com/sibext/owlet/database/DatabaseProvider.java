/**
 * This file is part of Owlet.
 * 
 * Owlet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * Owlet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with Owlet.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.sibext.owlet.database;

import com.sibext.owlet.helper.ArraysHelper;
import com.sibext.owlet.model.Base;
import com.sibext.owlet.model.TableStatistic;
import com.sibext.owlet.model.TableUsers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DatabaseProvider extends ContentProvider {
	public static final String DB_NAME = "NSDataBase";
	public static final String KEY_OF_TABLE_NAME = "keyTableName";
	private static final UriMatcher URI_MATCHER;
	private static final int USERS = 111;
	private static final int STATISTIC = 222;
	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(Base.URI, TableUsers.TABLE_NAME, USERS);
		URI_MATCHER.addURI(Base.URI, TableStatistic.TABLE_NAME, STATISTIC);
	}

	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ TableUsers.URI + "/" + TableUsers.TABLE_NAME);

	private static final int INDEX_TABLE_NAME_COLUMN = 0;
	private SQLiteDatabase db;

	@Override
	public boolean onCreate() {
		db = (new DatabaseHelper(getContext())).getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri url, ContentValues values) {
		String tableName = values.get(KEY_OF_TABLE_NAME).toString();
		values.remove(KEY_OF_TABLE_NAME);
		return insert(url, tableName, values);
	}

	public Uri insert(Uri url, String tableName, ContentValues values) {
		Long rowId = db.insert(tableName, TableUsers.TABLE_FIELD_NAME, values);
		if (rowId > 0) {
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		} else {
			throw new SQLException("Failed to insert row into " + url);
		}
	}

	@Override
	public synchronized Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		switch (URI_MATCHER.match(uri)) {
		case USERS: {
			String tableName = projection[INDEX_TABLE_NAME_COLUMN];
			int indexLastElement = projection.length;
			// projection = Arrays.copyOfRange(projection, 1, indexLastElement);
			projection = ArraysHelper.copyOfRange(projection, 1,
					indexLastElement);
			return query(uri, tableName, projection, selection, selectionArgs,
					sortOrder);
		}
		case STATISTIC: {
			return queryStatistic(uri, selection);
		}
		}
		return null;
	}

	public Cursor queryStatistic(Uri uri, String selection) {
		Cursor c = db.rawQuery("SELECT "
				+ TableStatistic.TABLE_FIELD_TASK_TYPE + ", "
				+ TableStatistic.TABLE_FIELD_COUNT_OF_CORRECT_ANSWERS + ", "
				+ TableStatistic.TABLE_FIELD_ATTEMPTS_COUNT + ", "
				+ TableStatistic.TABLE_FIELD_TASK_COMPLEX+ " from "
				+ TableStatistic.TABLE_NAME + " where "
				+ selection, null);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	public Cursor query(Uri uri, String tableName, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {

		Cursor c = db.query(tableName, projection, selection, selectionArgs,
				null, null, null);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		String tableName = values.get(KEY_OF_TABLE_NAME).toString();
		values.remove(KEY_OF_TABLE_NAME);
		return update(uri, tableName, values, selection, selectionArgs);
	}

	public int update(Uri uri, String tableName, ContentValues values,
			String selection, String[] selectionArgs) {
		int count = db.update(tableName, values, selection, selectionArgs);
		return count;
	}

}
