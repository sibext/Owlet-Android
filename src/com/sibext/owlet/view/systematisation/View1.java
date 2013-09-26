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
 * Прорисовываются стрелки
 * 
 * @author Mike Osipov <osipov@sibext.com>
 * 
 */
public class View1 extends BaseTaskImageView {
	private final int ARROW_LEFT = 0;
	private final int ARROW_DOWN = 1;
	private final int ARROW_RIGHT = 2;
	private final int ARROW_UP = 3;
	private final int ARROW_LEFT_FILL = 4;
	private final int ARROW_DOWN_FILL = 5;
	private final int ARROW_RIGHT_FILL = 6;
	private final int ARROW_UP_FILL = 7;

	public View1(Context context, Renderer renderer) {
		super(context, renderer);
		this.rendererIdLimit = 8;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();
		switch (getRenderId()) {
		case ARROW_LEFT:
			TaskImageHelper.Arrows.drawArrowLeft(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		case ARROW_DOWN:
			TaskImageHelper.Arrows.drawArrowDown(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		case ARROW_RIGHT:
			TaskImageHelper.Arrows.drawArrowRight(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		case ARROW_UP:
			TaskImageHelper.Arrows.drawArrowUp(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		case ARROW_LEFT_FILL:
			TaskImageHelper.Arrows.drawArrowLeftFill(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		case ARROW_DOWN_FILL:
			TaskImageHelper.Arrows.drawArrowDownFill(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		case ARROW_RIGHT_FILL:
			TaskImageHelper.Arrows.drawArrowRightFill(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		case ARROW_UP_FILL:
			TaskImageHelper.Arrows.drawArrowUpFill(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		default:
			break;
		}
		super.onDraw(canvas);
	}
}
