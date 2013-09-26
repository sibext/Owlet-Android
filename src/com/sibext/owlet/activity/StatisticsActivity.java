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

package com.sibext.owlet.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.sibext.owlet.R;
import com.sibext.owlet.activity.adapter.ExpandableStatisticAdapter;
import com.sibext.owlet.database.DatabaseHelper;
import com.sibext.owlet.database.DatabaseProvider;
import com.sibext.owlet.model.TableStatistic;
import com.sibext.owlet.model.TableUsers;
import com.sibext.owlet.model.statistic.TaskModel;
import com.sibext.owlet.model.statistic.UsersModel;
import com.sibext.owlet.tasks.Task;

public class StatisticsActivity extends BaseOwletActivity {
	private HashMap<Integer, UsersModel> users;
	private int curId;
	private int positionOfCurrentUser = 0;
	private ExpandableStatisticAdapter adapter;
	private int lastExpandedGroupPosition;
	ArrayList<Map<String, String>> groupData;
	ArrayList<ArrayList<Map<String, String>>> childData;
	ArrayList<Map<String, String>> childDataItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setLanguageFromPref();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistic);
		curId = getIntent().getIntExtra(MainActivity.ID, 0);

		final ExpandableListView list = (ExpandableListView) findViewById(R.id.statistic_exp_list_view);
		groupData = new ArrayList<Map<String, String>>();
		childData = new ArrayList<ArrayList<Map<String, String>>>();
		
		
		String[] projection = { TableUsers.TABLE_NAME };
		Cursor cursor = getContentResolver().query(
				DatabaseProvider.CONTENT_URI, projection, null, null, null);
		cursor.moveToFirst();
		int position=0;
		users = new HashMap<Integer, UsersModel>();
		do{
			UsersModel user = new UsersModel();
			int id = cursor.getInt(cursor.getColumnIndex(TableUsers.TABLE_FIELD_ID));
			String name = cursor.getString(cursor.getColumnIndex(TableUsers.TABLE_FIELD_NAME));
			String age = cursor.getString(cursor.getColumnIndex(TableUsers.TABLE_FIELD_AGE));
			if(id==curId){
				positionOfCurrentUser=position;
			}
			// Add group
			HashMap<String, String> m = new HashMap<String, String>();
			m.put(TableUsers.TABLE_FIELD_NAME, name);
			m.put(TableUsers.TABLE_FIELD_AGE, age);
			groupData.add(m);
			//
			childDataItem = new ArrayList<Map<String, String>>();
 			for(int i=0; i<DatabaseHelper.taskTypes.length;i++){
				TaskModel task = new TaskModel();
				String selection = TableStatistic.TABLE_FIELD_ID + "=" + id + " AND "
						  + TableStatistic.TABLE_FIELD_TASK_TYPE + "=" + DatabaseHelper.taskTypes[i];
				Cursor child = getContentResolver().query(TableStatistic.CONTENT_URI, null,selection, null, null);
				child.moveToFirst();
				do{					
					int complex = child.getInt(child.getColumnIndex(TableStatistic.TABLE_FIELD_TASK_COMPLEX));
					int correct = child.getInt(child.getColumnIndex(TableStatistic.TABLE_FIELD_COUNT_OF_CORRECT_ANSWERS));
					int attempt = child.getInt(child.getColumnIndex(TableStatistic.TABLE_FIELD_ATTEMPTS_COUNT));
					switch (complex) {
					case Task.COMPLEXITY_LOW:
						task.low_correct = correct;
						task.low_attempt = attempt;
						break;
					case Task.COMPLEXITY_MEDIUM:
						task.med_correct = correct;
						task.med_attempt = attempt;
						break;
					case Task.COMPLEXITY_HIGH:
						task.hight_correct= correct;
						task.hight_attempt = attempt;
						break;
					default:
						break;
					}
				}
				while(child.moveToNext());
				child.close();
				
				String type = "";
				switch (DatabaseHelper.taskTypes[i]) {
				case Task.COMPARE_TASK_TYPE:
					type = getResources().getString(R.string.compare);
					break;
				case Task.SYSTEMATISATION_TASK_TYPE:
					type = getResources().getString(R.string.systematisation);
					break;
				case Task.CONCLUSION_TASK_TYPE:
					type = getResources().getString(R.string.conclusion);
					break;
				case Task.MAGICSUARE_TASK_TYPE:
					type = getResources().getString(R.string.magic_square);
					break;
				}
				user.addTask(type, task);
				HashMap<String, String> c = new HashMap<String, String>();
				c.put(TableStatistic.TABLE_FIELD_TASK_TYPE, type);
				childDataItem.add(c);

			}
			childData.add(childDataItem);
			users.put(position, user);
			position++;
		}
		while(cursor.moveToNext());
		cursor.close();

		//
		int groupLayout = R.layout.statistic_list_item;
		int groupLayoutExp = R.layout.statistic_list_item_expended;
		String[] groupFrom = { TableUsers.TABLE_FIELD_NAME,
				TableUsers.TABLE_FIELD_AGE };
		int[] groupTo = { R.id.statistic_list_item_name,
				R.id.statistic_list_item_age };

		int childLayout = R.layout.statistic_list_item_child;
		String[] childFrom = { TableStatistic.TABLE_FIELD_TASK_TYPE};
		int[] childTo = { R.id.statistic_list_item_type};


		
		
		adapter = new ExpandableStatisticAdapter(
				this, groupData,groupLayoutExp, groupLayout, groupFrom, groupTo, 
				childData, childLayout, childFrom, childTo, users);
	
		lastExpandedGroupPosition = positionOfCurrentUser;
		list.post(new Runnable() {			
			@Override
			public void run() {
				list.smoothScrollToPosition(positionOfCurrentUser);
				list.expandGroup(positionOfCurrentUser);
			}
		});
		
		list.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				if(groupPosition!=lastExpandedGroupPosition){
					list.collapseGroup(lastExpandedGroupPosition);
				}				
				lastExpandedGroupPosition = groupPosition;
			}
		});
		 
		list.setAdapter(adapter);

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), ScreenOfTaskActivity.class);
		intent.putExtra(MainActivity.ID, curId);
		startActivity(intent);
		finish();
		super.onBackPressed();
	}

	@Override
	protected void onMainMenuSelected(int id) {
		switch (id) {
		case R.id.menu_exit: {
			onExit();
			break;
		}
		case R.id.menu_logout: {
			onExit();
			onAuthorizationActivity();
			break;
		}
		case R.id.menu_settings: {
			onSettingsActivity();
			break;
		}
		case R.id.menu_back: {
			onBackPressed();
			break;
		}
		case R.id.menu_about: {
			onAboutActivity();
			break;
		}
		}
	}

	@Override
	protected int getMenuId() {
		return R.menu.statistic;
	}

}
