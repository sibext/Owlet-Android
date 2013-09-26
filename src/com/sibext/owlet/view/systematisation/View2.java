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
 * Рисуются смайлы
 * 
 * @author Mike Osipov <osipov@sibext.com>
 * 
 */
public class View2 extends BaseTaskImageView {
	// _FILL означает, что глаза закрашены
	private final int SMILE = 0;
	private final int NEUTRAL = 1;
	private final int SAD = 2;
	private final int SMILE_FILL = 3;
	private final int NEUTRAL_FILL = 4;
	private final int SAD_FILL = 5;

	public View2(Context context, Renderer renderer) {
		super(context, renderer);
		rendererIdLimit = 6;
	}

	public void setRendererIdLimit(int z) {
		if (rendererIdLimit > z)
			this.rendererIdLimit = z;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();
		/*
		 * Paint paint = new Paint(); paint.setColor(Color.WHITE); RectF rect =
		 * new RectF(0, 0, w, h); canvas.drawRoundRect(rect, 6, 6, paint);
		 */
		switch (getRenderId()) {
		case SMILE:
			TaskImageHelper.Smiles.drawSmile(canvas, getColorFlag(),
					getColor(), w, h, false);
			break;
		case NEUTRAL:
			TaskImageHelper.Smiles.drawSmileNeutral(canvas, getColorFlag(),
					getColor(), w, h, false);
			break;
		case SAD:
			TaskImageHelper.Smiles.drawSmileSad(canvas, getColorFlag(),
					getColor(), w, h, false);
			break;
		case SMILE_FILL:
			TaskImageHelper.Smiles.drawSmile(canvas, getColorFlag(),
					getColor(), w, h, true);
			break;
		case NEUTRAL_FILL:
			TaskImageHelper.Smiles.drawSmileNeutral(canvas, getColorFlag(),
					getColor(), w, h, true);
			break;
		case SAD_FILL:
			TaskImageHelper.Smiles.drawSmileSad(canvas, getColorFlag(),
					getColor(), w, h, true);
			break;
		default:
			break;
		}

		super.onDraw(canvas);
	}
}
