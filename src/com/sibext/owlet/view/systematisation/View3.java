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

package com.sibext.owlet.view.systematisation;

import com.sibext.owlet.helper.TaskImageHelper;
import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.renderer.Renderer;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Рисуются треугольники с либо верхней, либо нижней закрашенной частью
 * 
 * @author Mike Osipov <osipov@sibext.com>
 *
 */

public class View3 extends BaseTaskImageView {
	// TRIANGLE_[направоение]_FILL_[часть, которая закрашена (верхняя/нижняя)]
	private final int TRIANGLE_UP_FILL_UP = 0;
	private final int TRIANGLE_LEFT_FILL_UP = 1;
	private final int TRIANGLE_DOWN_FILL_UP = 2;
	private final int TRIANGLE_RIGHT_FILL_UP = 3;
	private final int TRIANGLE_UP_FILL_DOWN = 4;
	private final int TRIANGLE_LEFT_FILL_DOWN = 5;
	private final int TRIANGLE_DOWN_FILL_DOWN = 6;
	private final int TRIANGLE_RIGHT_FILL_DOWN = 7;

	public View3(Context context, Renderer renderer) {
		super(context, renderer);
		rendererIdLimit = 6;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();		
		switch (getRenderId()) {
		case TRIANGLE_UP_FILL_UP:
			TaskImageHelper.Triangles.drawTriangleUpFillUp(canvas,getColorFlag(),getColor(), w, h);
			break;
		case TRIANGLE_LEFT_FILL_UP:
			TaskImageHelper.Triangles.drawTriangleLeftFillUp(canvas,getColorFlag(),getColor(), w, h);
			break;
		case TRIANGLE_DOWN_FILL_UP:
			TaskImageHelper.Triangles.drawTriangleDownFillUp(canvas,getColorFlag(),getColor(), w, h);
			break;			
		case TRIANGLE_RIGHT_FILL_UP:
			TaskImageHelper.Triangles.drawTriangleRightFillUp(canvas,getColorFlag(),getColor(), w, h);
			break;
		case TRIANGLE_UP_FILL_DOWN:
			TaskImageHelper.Triangles.drawTriangleUpFillDown(canvas,getColorFlag(),getColor(), w, h);
			break;
		case TRIANGLE_LEFT_FILL_DOWN:
			TaskImageHelper.Triangles.drawTriangleLeftFillDown(canvas,getColorFlag(),getColor(), w, h);
			break;
		case TRIANGLE_DOWN_FILL_DOWN:
			TaskImageHelper.Triangles.drawTriangleDownFillDown(canvas,getColorFlag(),getColor(), w, h);
			break;
		case TRIANGLE_RIGHT_FILL_DOWN:
			TaskImageHelper.Triangles.drawTriangleRightFillDown(canvas,getColorFlag(),getColor(), w, h);
			break;
		default:
			break;
		}
		super.onDraw(canvas);
	}
}
