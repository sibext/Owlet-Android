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

package com.sibext.owlet.activity.adapter;

import com.sibext.owlet.R;
import com.sibext.owlet.database.DatabaseHelper;
import com.sibext.owlet.model.TableStatistic;
import com.sibext.owlet.model.TableUsers;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleCursorTreeAdapter;

public class StatisticAdapter extends SimpleCursorTreeAdapter {
	private Context context;
	private SQLiteDatabase db;

	public StatisticAdapter(Context context, Cursor cursor, int groupLayout,
			String[] groupFrom, int[] groupTo, int childLayout,
			String[] childFrom, int[] childTo) {
		super(context, cursor, groupLayout, groupFrom, groupTo, childLayout,
				childFrom, childTo);
		this.context = context;
		DatabaseHelper dbhelp = new DatabaseHelper(context);
		db = dbhelp.getReadableDatabase();		
	}
	public StatisticAdapter(Context context, Cursor cursor, int groupLayout,
			String[] groupFrom, int[] groupTo, int childLayout,
			String[] childFrom, int[] childTo, ContentResolver cr) {
		super(context, cursor, groupLayout, groupFrom, groupTo, childLayout,
				childFrom, childTo);
		this.context = context;
		DatabaseHelper dbhelp = new DatabaseHelper(context);
		
		db = dbhelp.getReadableDatabase();
	}

	@Override
	protected synchronized Cursor getChildrenCursor(Cursor groupCursor) {
		Cursor res=groupCursor;
		int idIndex = groupCursor.getColumnIndex(TableUsers.TABLE_FIELD_ID);
		int id = groupCursor.getInt(idIndex);

		if (null != context) {
			res = context.getContentResolver().query(TableStatistic.CONTENT_URI, null,String.valueOf(id), null, null);
		}
		return res;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View res = super.getChildView(groupPosition, childPosition, isLastChild,
				convertView, parent);
		View item = res.findViewById(R.id.item);
		if(isLastChild){
			item.setBackgroundResource(R.drawable.statistic_shape_item_finish);
		}	
		else{
			item.setBackgroundResource(R.drawable.statistic_shape_item);
		}
		return res;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View res = super.getGroupView(groupPosition, isExpanded, convertView, parent);
		View item = res.findViewById(R.id.parent_item);
		ImageView indicator = (ImageView)res.findViewById(R.id.statistic_indicator);
		if(isExpanded){
			LayoutParams params = (LayoutParams)item.getLayoutParams();
			params.bottomMargin=0;
			item.setBackgroundResource(R.drawable.statistic_shape_exp);
			indicator.setImageResource(R.drawable.indicatorup1);
		} else {
			LayoutParams params = (LayoutParams)item.getLayoutParams();
			params.bottomMargin=3;
			item.setBackgroundResource(R.drawable.statistic_shape_collapse);
			indicator.setImageResource(R.drawable.indicatordn1);
		}
		return res;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	public void closeDataBase(){
		db.close();
	}

	@Override
	public void changeCursor(Cursor cursor) {
		super.changeCursor(cursor);
	}
}
