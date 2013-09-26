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

/**
 * Набор божьих коровок
 * 
 * @author Mike Osipov <osipov@sibext.com>
 */
public class LadybugView extends TaskImageViewFromResourse{

	public LadybugView(Context context, Renderer renderer) {
		super(context, renderer);
	}

	@Override
	protected int[] getResourcesIds() {
		return new int[]{
				R.drawable.ladybug1,
				R.drawable.ladybug2,
				R.drawable.ladybug3,
				R.drawable.ladybug4,
				R.drawable.ladybug5,
				R.drawable.ladybug6,
				R.drawable.ladybug7,
				R.drawable.ladybug8,
				R.drawable.ladybug9,
		};
	}

}
