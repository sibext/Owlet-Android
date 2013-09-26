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
public class View3 extends BaseTaskImageView{
	private final int FILL_CIRCLE= 0;
	private final int CROSSED_TWO_LINES_CIRCLE= 1;
	private final int CIRCLE= 2;
	private final int CROSSED_ONE_LINES_CIRCLE = 3;
	
	public View3(Context context, Renderer renderer) {
		super(context, renderer);
		this.rendererIdLimit = 4;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();

		switch (getRenderId()) {
		case FILL_CIRCLE:
			TaskImageHelper.Geometry.drawCircleFill(canvas,getColorFlag(),getColor(), w, h);
			break;
		case CROSSED_TWO_LINES_CIRCLE:
			TaskImageHelper.Geometry.drawCrossedTwoLinesCircle(canvas,getColorFlag(),getColor(), w, h);
			break;
		case CIRCLE:
			TaskImageHelper.Geometry.drawCircle(canvas,getColorFlag(),getColor(), w, h);
			break;
		case CROSSED_ONE_LINES_CIRCLE:
			TaskImageHelper.Geometry.drawCrossedOneLinesCircle(canvas,getColorFlag(),getColor(), w, h);
			break;
		default:
			break;
		}
	}

}
