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

public class View1 extends BaseTaskImageView {
	private final int DRAW_Z = 0;
	private final int DRAW_TRIANGLE_DIVIDED_LINE = 1;
	private final int DRAW_LABYRINTH= 2;
	
	public View1(Context context, Renderer renderer) {
		super(context, renderer);
		rendererIdLimit = 6; 
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();
	
		switch (getRenderId()) {
		case DRAW_Z:
			TaskImageHelper.Geometry.drawZ(canvas, getColorFlag(), getColor(), w, h);
			break;
		
		case DRAW_TRIANGLE_DIVIDED_LINE:	
			TaskImageHelper.Geometry.drawTraingleDividedByLine(canvas, getColorFlag(), getColor(), w, h);
			break;
			
		case DRAW_LABYRINTH:
			TaskImageHelper.Geometry.drawLabirint(canvas, getColorFlag(), getColor(), w, h);
			break;

		default:
			break;
		}
		
		super.onDraw(canvas);
	}

}
