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

public class View2 extends BaseTaskImageView{
	//Кости
	private final int ONE= 0;
	private final int TWO= 1;
	private final int THREE= 2;
	private final int FOUR= 3;
	private final int FIVE= 4;
	private final int SIX = 5;

	public View2(Context context, Renderer renderer) {
		super(context, renderer);
		this.rendererIdLimit = 6;
	}


	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();
		switch (getRenderId()) {
		case ONE:
			TaskImageHelper.Dices.drawOne(canvas,getColorFlag(),getColor(), w, h);
			break;
		case TWO:
			TaskImageHelper.Dices.drawTwo(canvas,getColorFlag(),getColor(), w, h);
			break;
		case THREE:
			TaskImageHelper.Dices.drawThree(canvas,getColorFlag(),getColor(), w, h);
			break;
		case FOUR:
			TaskImageHelper.Dices.drawFour(canvas,getColorFlag(),getColor(), w, h);
			break;
		case FIVE:
			TaskImageHelper.Dices.drawFive(canvas,getColorFlag(),getColor(), w, h);
			break;
		case SIX:
			TaskImageHelper.Dices.drawSix(canvas,getColorFlag(),getColor(), w, h);
			break;		
		default:
			break;
		}
	}

}
