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

package com.sibext.owlet.view.resourses;


import android.content.Context;

import com.sibext.owlet.R;
import com.sibext.owlet.view.TaskImageViewFromResourse;
import com.sibext.owlet.view.renderer.Renderer;

public class HouseView extends TaskImageViewFromResourse{

	public HouseView(Context context, Renderer renderer) {
		super(context, renderer);
	}

	@Override
	protected int[] getResourcesIds() {
		return new int[]{
				R.drawable.house1,
				R.drawable.house2,
				R.drawable.house3,
				R.drawable.house4,
				R.drawable.house5,
				R.drawable.house6,
				R.drawable.house7,
				R.drawable.house8,
				R.drawable.house9,
				R.drawable.house10,
				R.drawable.house11,
				R.drawable.house12,
				R.drawable.house13,
				R.drawable.house14,
				R.drawable.house15,
		};
	}

}
