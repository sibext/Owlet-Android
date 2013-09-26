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

package com.sibext.owlet.view.magic_square;

import com.sibext.owlet.helper.TaskImageHelper;
import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.renderer.Renderer;

import android.content.Context;
import android.graphics.Canvas;

public class View3 extends BaseTaskImageView {
	private final int DRAW_SUN = 0;
	private final int DRAW_MOON = 1;
	private final int DRAW_STAR = 2;
		
	public View3(Context context, Renderer renderer) {
		super(context, renderer);
		rendererIdLimit = 3; 
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();

		switch (getRenderId()) {
		case DRAW_SUN:
			TaskImageHelper.Geometry.drawSun(canvas, getColorFlag(), getColor(), w, h);
			break;
		case DRAW_MOON:	
			TaskImageHelper.Geometry.drawMoon(canvas, getColorFlag(), getColor(), w, h);
			break;
		case DRAW_STAR:
			TaskImageHelper.Geometry.drawStar(canvas, getColorFlag(), getColor(), w, h);
			break;
		default:
			break;
		}
		
		super.onDraw(canvas);
	}

}
