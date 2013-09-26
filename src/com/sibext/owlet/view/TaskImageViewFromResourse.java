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

package com.sibext.owlet.view;

import com.sibext.owlet.view.renderer.Renderer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * The abstract set of task image views from android resources
 * 
 * @author Nikolay Moskvin <moskvin@sibext.com>
 * 
 */
abstract public class TaskImageViewFromResourse extends BaseTaskImageView {
	protected int rendererId = 0;
	private Context context;

	public TaskImageViewFromResourse(Context context, Renderer renderer) {
		super(context, renderer);
		this.setDrawingCacheEnabled(true);
		this.context = context;
		reDraw();
	}

	@Override
	public void update() {
		reDraw();
	}

	private void reDraw() {
		this.rendererId = getRenderId();
		rendererIdLimit = getResourcesIds().length;
		Resources res = context.getResources();
		Drawable drawable = res.getDrawable(getResourcesIds()[rendererId]);
		this.setImageDrawable(drawable);
	}

	abstract protected int[] getResourcesIds();
}
