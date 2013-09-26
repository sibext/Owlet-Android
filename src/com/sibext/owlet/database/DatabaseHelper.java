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

import com.sibext.owlet.R;
import com.sibext.owlet.model.TableStatistic;
import com.sibext.owlet.model.TableTaskTypes;
import com.sibext.owlet.model.TableUsers;
import com.sibext.owlet.tasks.Task;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {
	private static final int DB_VERSION = 6;
	Context context;
	public static int[] taskTypes = new int[] {
			Task.MAGICSUARE_TASK_TYPE,
			Task.COMPARE_TASK_TYPE, 
			Task.CONCLUSION_TASK_TYPE,
			Task.SYSTEMATISATION_TASK_TYPE};
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	public DatabaseHelper(Context context) {
		super(context, DatabaseProvider.DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TableUsers.TABLE_NAME + " ( "
				+ TableUsers.TABLE_FIELD_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ TableUsers.TABLE_FIELD_NAME + " TEXT, "
				+ TableUsers.TABLE_FIELD_AGE + " TINYINT, "
				+ TableUsers.TABLE_FIELD_LOGIN + " TINYINT);");
		db.execSQL("CREATE TABLE " + TableStatistic.TABLE_NAME + " ( "
				+ TableStatistic.TABLE_FIELD_ID + " INTEGER, "
				+ TableStatistic.TABLE_FIELD_TASK_TYPE + " INTEGER, "
				+ TableStatistic.TABLE_FIELD_TASK_COMPLEX + " INTEGER, "
				+ TableStatistic.TABLE_FIELD_COUNT_OF_CORRECT_ANSWERS
				+ " INTEGER, " + TableStatistic.TABLE_FIELD_ATTEMPTS_COUNT
				+ " INTEGER, FOREIGN KEY (" + TableStatistic.TABLE_FIELD_ID
				+ ") REFERENCES " + TableUsers.TABLE_NAME + "("
				+ TableUsers.TABLE_FIELD_ID + "));");

		db.execSQL("CREATE TABLE " + TableTaskTypes.TABLE_NAME + " ( "
				+ TableTaskTypes.TABLE_FIELD_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ TableTaskTypes.TABLE_FIELD_TASK_TYPE + " INTEGER,"
				+ TableTaskTypes.TABLE_FIELD_TASK_NAME + " TEXT);");
		fullTableTaskTypes(db);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TableUsers.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TableStatistic.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TableTaskTypes.TABLE_NAME);
		onCreate(db);
	}

	private void fullTableTaskTypes(SQLiteDatabase db) {
		String[] names = new String[] {
				context.getResources().getString(R.string.magic_square),
				context.getResources().getString(R.string.compare),
				context.getResources().getString(R.string.conclusion),
				context.getResources().getString(R.string.systematisation), };
		for (int i = 0; i < taskTypes.length; i++) {
			db.execSQL("INSERT INTO " + TableTaskTypes.TABLE_NAME + " ("
					+ TableTaskTypes.TABLE_FIELD_TASK_TYPE + ", "
					+ TableTaskTypes.TABLE_FIELD_TASK_NAME + ") VALUES ("
					+ taskTypes[i] + ", '" + names[i] + "')");
		}
	}
}
