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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sibext.owlet.R;
import com.sibext.owlet.model.statistic.TaskModel;
import com.sibext.owlet.model.statistic.UsersModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * The Statistics adapter
 * 
 * @author Nikolay Moskvin <moskvin@sibext.com>
 * 
 */
public class ExpandableStatisticAdapter extends SimpleExpandableListAdapter{
	private HashMap<Integer,UsersModel> users;	
	private Context context;
	
	public ExpandableStatisticAdapter(Context context,
			List<? extends Map<String, ?>> groupData, int expandedGroupLayout,
			int collapsedGroupLayout, String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, String[] childFrom,
			int[] childTo, HashMap<Integer,UsersModel> users) {
		super(context, groupData, expandedGroupLayout, collapsedGroupLayout, groupFrom,
				groupTo, childData, childLayout, childFrom, childTo);
		this.context = context;
		this.users = users;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View res = super.getChildView(groupPosition, childPosition, isLastChild,
				convertView, parent);
		final View resf = res;
		final int groupPositionf = groupPosition;
		View item = res.findViewById(R.id.item);
		//
		res.post(new Runnable() {
			@Override
			public void run() {
				TextView item_type = (TextView)resf.findViewById(R.id.statistic_list_item_type);
				String type = item_type.getText().toString();
				TaskModel task = users.get(groupPositionf).getTask(type);

				ProgressBar low = (ProgressBar)resf.findViewById(R.id.progress_low);
				ProgressBar med = (ProgressBar)resf.findViewById(R.id.progress_med);
				ProgressBar hight = (ProgressBar)resf.findViewById(R.id.progress_hight);
				TextView low_text = (TextView)resf.findViewById(R.id.progress_low_text);
				TextView med_text = (TextView)resf.findViewById(R.id.progress_med_text);
				TextView hight_text = (TextView)resf.findViewById(R.id.progress_hight_text);
				
				low.setMax(task.low_attempt);
				low.setProgress(task.low_correct);
				low_text.setText(task.low_correct+"/"+task.low_attempt);
				med.setMax(task.med_attempt);
				med.setProgress(task.med_correct);
				med_text.setText(task.med_correct+"/"+task.med_attempt);
				hight.setMax(task.hight_attempt);
				hight.setProgress(task.hight_correct);
				hight_text.setText(task.hight_correct+"/"+task.hight_attempt);
			}
		});
		//
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
		View res = super.getGroupView(groupPosition, isExpanded, convertView,
				parent);
		final View resf = res;
		final boolean isExpandedf = isExpanded;
		if (isExpandedf) {
			res.post(new Runnable() {
				@Override
				public void run() {
					FrameLayout rPart = (FrameLayout) resf
							.findViewById(R.id.statistic_shape_right_part);

					View item = resf.findViewById(R.id.parent_item);
					ImageView indicator = (ImageView) resf
							.findViewById(R.id.statistic_indicator);
					RelativeLayout tab = (RelativeLayout) resf
							.findViewById(R.id.tab);
					TableRow row = (TableRow) tab.findViewById(R.id.row);

					rPart.setBackgroundResource(R.drawable.statistic_shape_item);
					LayoutParams params = (LayoutParams) item.getLayoutParams();
					params.bottomMargin = 0;
					indicator.setImageResource(R.drawable.indicatorup1);
					tab.setBackgroundResource(R.drawable.statistic_shape_item);

					TextView text = (TextView)row.findViewById(R.id.row_text);
					TextView textL = (TextView)row.findViewById(R.id.row_text_left);
					TextView textR = (TextView)row.findViewById(R.id.row_text_right);
 
					textL.setText(context.getResources().getString(
							R.string.statistic_low_compl_title));
					text.setText(context.getResources().getString(
							R.string.statistic_medium_compl_title));
					textR.setText(context.getResources().getString(
							R.string.statistic_hight_compl_title));
				}
			});
		} else {
			res.post(new Runnable() {
				@Override
				public void run() {
					FrameLayout rPart = (FrameLayout) resf
							.findViewById(R.id.statistic_shape_right_part);

					View item = resf.findViewById(R.id.parent_item);
					ImageView indicator = (ImageView) resf
							.findViewById(R.id.statistic_indicator);
					RelativeLayout tab = (RelativeLayout) resf
							.findViewById(R.id.tab);
					TableRow row = (TableRow) tab.findViewById(R.id.row);

					rPart.setBackgroundResource(0);
					LayoutParams params = (LayoutParams) item.getLayoutParams();
					params.bottomMargin = 3;
					indicator.setImageResource(R.drawable.indicatordn1);
					TextView text = (TextView)row.findViewById(R.id.row_text);
					TextView textL = (TextView)row.findViewById(R.id.row_text_left);
					TextView textR = (TextView)row.findViewById(R.id.row_text_right);
					textL.setText("");
					text.setText("");
					textR.setText("");

					tab.setBackgroundResource(0);
				}
			});
		}

		return res;
	}
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
}
