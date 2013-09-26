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

import android.content.Context;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;

/**
 * The legacy popup implementation (early Android 3.0 API)
 * 
 * @author Nikolay Moskvin <moskvin@sibext.com>
 * 
 */
public class ListPopupWindowLegacy implements IListPopupWindow {

	public ListPopupWindowLegacy(Context context) {
	}

	@Override
	public void setBackgroundDrawable(int drawableId) {
		
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		
	}

	@Override
	public void setAnchorView(View view) {
	}

	@Override
	public void show() {
	}

	@Override
	public void dismiss() {
	}

	@Override
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
	}

}
