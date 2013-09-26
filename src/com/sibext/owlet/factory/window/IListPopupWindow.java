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

import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;

/**
 * The popup window interface
 * 
 * @author Nikolay Moskvin <moskvin@sibext.com>
 * 
 */
public interface IListPopupWindow {

	void setBackgroundDrawable(int drawableId);

	void setAdapter(ListAdapter adapter);

	void setAnchorView(View view);

	void show();

	void dismiss();

	void setOnItemClickListener(OnItemClickListener onItemClickListener);
}
