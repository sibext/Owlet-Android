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
 * Прорисовываются человечки
 * 
 * @author Mike Osipov <osipov@sibext.com>
 * 
 */
public class PeoplesSetView extends BaseTaskImageView {

	// _FILL - закрашена голова
	private final int MAN_HEANDS_UP = 0;
	private final int MAN_HEANDS_RIGHT = 1;
	private final int MAN_HEANDS_DOWN = 2;
	private final int MAN_HEANDS_UP_FILL = 3;
	private final int MAN_HEANDS_RIGHT_FILL = 4;
	private final int MAN_HEANDS_DOWN_FILL = 5;

	public PeoplesSetView(Context context, Renderer renderer) {
		super(context, renderer);
		rendererIdLimit = 6;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();
		//
		switch (getRenderId()) {
		case MAN_HEANDS_UP:
			TaskImageHelper.Peoples.drawPeopleHandsUp(canvas,getColorFlag(),getColor(), w, h, false);
			break;
		case MAN_HEANDS_RIGHT:
			TaskImageHelper.Peoples.drawPeopleHandsRight(canvas,getColorFlag(),getColor(), w, h, false);
			break;
		case MAN_HEANDS_DOWN:
			TaskImageHelper.Peoples.drawPeopleHandsDown(canvas,getColorFlag(),getColor(), w, h, false);
			break;
		case MAN_HEANDS_UP_FILL:
			TaskImageHelper.Peoples.drawPeopleHandsUp(canvas,getColorFlag(),getColor(), w, h, true);
			break;
		case MAN_HEANDS_RIGHT_FILL:
			TaskImageHelper.Peoples.drawPeopleHandsRight(canvas,getColorFlag(),getColor(), w, h, true);
			break;
		case MAN_HEANDS_DOWN_FILL:
			TaskImageHelper.Peoples.drawPeopleHandsDown(canvas,getColorFlag(),getColor(), w, h, true);
			break;
		default:
			break;
		}
		super.onDraw(canvas);
	}
}
