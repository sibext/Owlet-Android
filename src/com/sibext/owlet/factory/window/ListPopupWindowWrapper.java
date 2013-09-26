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

package com.sibext.owlet.factory.window;

import com.sibext.owlet.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;

@TargetApi(11)
public class ListPopupWindowWrapper implements IListPopupWindow {
	private Context context;
	private ListPopupWindow listPopupWindow;

	public ListPopupWindowWrapper(Context context) {
		this.context = context;
		int windowWidth = (int)context.getResources().getDimension(R.dimen.list_popup_win_width);
		listPopupWindow = new ListPopupWindow(context);
		listPopupWindow.setWidth(windowWidth);

	}

	@Override
	public void setBackgroundDrawable(int drawableId) {
		listPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(drawableId));
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		listPopupWindow.setAdapter(adapter);
	}

	@Override
	public void setAnchorView(View view) {
		listPopupWindow.setAnchorView(view);
	}

	@Override
	public void show() {
		listPopupWindow.show();
	}

	@Override
	public void dismiss() {
		listPopupWindow.dismiss();
	}

	@Override
	public void setOnItemClickListener(OnItemClickListener clickListener) {
		listPopupWindow.setOnItemClickListener(clickListener);
	}

}
