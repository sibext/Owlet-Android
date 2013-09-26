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

import java.util.List;

import com.sibext.owlet.R;
import com.sibext.owlet.activity.adapter.PopupMenuAdapter.PopupMenuItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PopupMenuAdapter extends ArrayAdapter<PopupMenuItem> {

	private final Context context;
	private final int layoutResourceId;

	public PopupMenuAdapter(Context context, List<PopupMenuItem> data) {
		super(context, R.layout.menu_item, data);
		this.layoutResourceId = R.layout.menu_item;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final PopupMenuViewHolder holder; 
		final PopupMenuItem item = getItem(position);

		if (null == convertView) {
			holder = new PopupMenuViewHolder();
			convertView = View.inflate(context, layoutResourceId, null);
			holder.text = (TextView) convertView.findViewById(R.id.menu_item_text);
			convertView.setTag(holder);
		} else {
			holder = (PopupMenuViewHolder) convertView.getTag();
		}

		holder.text.setText(item.textResId);
		holder.text.setCompoundDrawablesWithIntrinsicBounds(item.iconResId, 0, 0, 0);

		return convertView;
	}

	private class PopupMenuViewHolder {
		public TextView text;
	}

	public static class PopupMenuItem {
		public int resId;
		public int iconResId;
		public int textResId;

		public PopupMenuItem(int resId, int iconResId, int textResId) {
			this.resId = resId;
			this.iconResId = iconResId;
			this.textResId = textResId;
		}
		
	}

}
