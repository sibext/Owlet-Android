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

import com.sibext.owlet.R;
import com.sibext.owlet.view.TaskImageViewFromResourse;
import com.sibext.owlet.view.renderer.Renderer;

import android.content.Context;

public class SnowmanView extends TaskImageViewFromResourse {

	public SnowmanView(Context context, Renderer renderer) {
		super(context, renderer);
	}

	@Override
	protected int[] getResourcesIds() {
		return new int[]{
				R.drawable.snowman1,
				R.drawable.snowman2,
				R.drawable.snowman3,
				R.drawable.snowman4,
				R.drawable.snowman5,
				R.drawable.snowman6,
				R.drawable.snowman7,
				R.drawable.snowman8,
		};
	}

}
