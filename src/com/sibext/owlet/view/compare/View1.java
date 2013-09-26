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

package com.sibext.owlet.view.compare;

import com.sibext.owlet.helper.TaskImageHelper;
import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.renderer.Renderer;

import android.content.Context;
import android.graphics.Canvas;
public class View1 extends BaseTaskImageView{
	private final int CIRCLE = 0;
	private final int TRIANGLE = 1;
	private final int SQUARE = 2;
	/**
	 * Крест
	 */
	private final int CROSS = 3;
	/**
	 * Три параллельные линии
	 */
	private final int PARALLEL_LINES= 4;
	/**
	 * Квадрат с крестиком внутри
	 */
	private final int CROSSED_SQUARE= 5;
	
	public View1(Context context, Renderer renderer) {
		super(context, renderer);
		this.rendererIdLimit = 6;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();
		int cx = (int)(w/2);
		int cy = (int)(h/2);
		int radius = (int)(h/3);
		switch (getRenderId()) {
		case CIRCLE:
			TaskImageHelper.Geometry.drawCircle(canvas,getColorFlag(),getColor(), w, h);
			break;
		case TRIANGLE:
			TaskImageHelper.Geometry.drawTriangle(canvas,getColorFlag(),getColor(), w, h);
			break;
		case SQUARE:
			TaskImageHelper.Geometry.drawSquare(canvas,getColorFlag(),getColor(), w, h);
			break;
		case CROSS:
			TaskImageHelper.Geometry.drawCross(canvas,getColorFlag(),getColor(), w, h);
			break;
		case PARALLEL_LINES:
			TaskImageHelper.Geometry.drawParallelLines(canvas,getColorFlag(),getColor(), w, h);			
			break;
		case CROSSED_SQUARE:
			TaskImageHelper.Geometry.drawCrossedSquare(canvas,getColorFlag(),getColor(), w, h);
			break;		
		default:
			break;
		}
	}
}
