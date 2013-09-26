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
import com.sibext.owlet.activity.adapter.LanguageAdapter.LanguageItem;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * The Language adapter
 * 
 * @author Nikolay Moskvin <moskvin@sibext.com>
 * 
 */
public class LanguageAdapter extends ArrayAdapter<LanguageItem> {

	public LanguageAdapter(Context context, List<LanguageItem> data) {
		super(context, R.layout.simple_spinner_item, data);
	}

	public static class LanguageItem {
		public String key;
		public String value;

		public LanguageItem(String key, String value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

}
