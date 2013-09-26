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
 * Рисуются углы
 * 
 * @author Mike Osipov <osipov@sibext.com>
 *
 */
public class View5 extends BaseTaskImageView {
	private final int UP_LEFT_CORNER = 0;
	private final int UP_RIGHT_CORNER = 1;
	private final int DOWN_RIGHT_CORNER = 2;
	private final int DOWN_LEFT_CORNER = 3;
	private final int UP_LEFT_CORNER_FILL = 4;
	private final int UP_RIGHT_CORNER_FILL = 5;
	private final int DOWN_RIGHT_CORNER_FILL = 6;
	private final int DOWN_LEFT_CORNER_FILL = 7;

	public View5(Context context, Renderer renderer) {
		super(context, renderer);
		rendererIdLimit = 8;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();		
		switch (getRenderId()) {
		case UP_LEFT_CORNER:
			TaskImageHelper.Corners.drawUpLeftCorner(canvas,getColorFlag(),getColor(), w, h, false);
			break;
		case UP_LEFT_CORNER_FILL:
			TaskImageHelper.Corners.drawUpLeftCorner(canvas,getColorFlag(),getColor(), w, h, true);
			break;
		case UP_RIGHT_CORNER:
			TaskImageHelper.Corners.drawUpRightCorner(canvas,getColorFlag(),getColor(), w, h, false);
			break;
		case UP_RIGHT_CORNER_FILL:
			TaskImageHelper.Corners.drawUpRightCorner(canvas,getColorFlag(),getColor(), w, h, true);
			break;
		case DOWN_RIGHT_CORNER:
			TaskImageHelper.Corners.drawDownRightCorner(canvas,getColorFlag(),getColor(), w, h, false);
			break;
		case DOWN_RIGHT_CORNER_FILL:
			TaskImageHelper.Corners.drawDownRightCorner(canvas,getColorFlag(),getColor(), w, h, true);
			break;
		case DOWN_LEFT_CORNER:
			TaskImageHelper.Corners.drawDownLeftCorner(canvas,getColorFlag(),getColor(), w, h, false);
			break;
		case DOWN_LEFT_CORNER_FILL:
			TaskImageHelper.Corners.drawDownLeftCorner(canvas,getColorFlag(),getColor(), w, h, true);
			break;
		default:
			break;
		}
		super.onDraw(canvas);
	}
}
