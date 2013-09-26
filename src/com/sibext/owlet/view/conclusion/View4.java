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

package com.sibext.owlet.view.conclusion;

import java.util.ArrayList;

import com.sibext.owlet.helper.TaskImageHelper;
import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.renderer.Renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class View4 extends BaseTaskImageView {
	private static final int DRAW_WAVE_LINE = 0;
	private static final int DRAW_ARROW = 1;
	private static final int DRAW_DIRECT_LINE = 2;
	private static final int DRAW_WAVE_RECTANGLE = 3;
	private static final int DRAW_ARROW1 = 4;
	private static final int DRAW_RECTANGLE_ANSW = 5;
	private static final int DRAW_RECTANGLE = 6;
	private static final int DRAW_TWO_WAVE_LINES = 7;
	private static final int DRAW_TWO_LINES = 8;
	private static final int DRAW_FILL_RECTANGLE = 9;

	public static int EXAMPLE_ID;
	public static int EXAMPLE_ANSWER_ID;
	public static int TEST_ID;
	public static int TEST_ANSWER_ID;
	public static ArrayList<Integer> variants;

	public View4(Context context, Renderer renderer) {
		super(context, renderer);
		rendererIdLimit = 6;
	}
	
	static{
		EXAMPLE_ID = DRAW_WAVE_LINE;
		EXAMPLE_ANSWER_ID = DRAW_DIRECT_LINE;
		TEST_ID = DRAW_WAVE_RECTANGLE;
		TEST_ANSWER_ID = DRAW_RECTANGLE_ANSW;
		variants = new ArrayList<Integer>();
		variants.add(DRAW_TWO_WAVE_LINES);
		variants.add(DRAW_TWO_LINES);
		variants.add(DRAW_FILL_RECTANGLE);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();

		switch (getRenderId()) {
		case DRAW_WAVE_LINE:
			TaskImageHelper.Geometry.drawTwoWaveLines(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		case DRAW_ARROW:

			Paint paint1 = new Paint();
			paint1.setAntiAlias(true);
			TaskImageHelper.Arrow.drawArrow(canvas, w, h, paint1);

			break;

		case DRAW_DIRECT_LINE:
			TaskImageHelper.Geometry.drawTwoDirectLines(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		case DRAW_WAVE_RECTANGLE:
			TaskImageHelper.Geometry.drawWaveRectangle(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		case DRAW_ARROW1:

			Paint paint4 = new Paint();
			paint4.setAntiAlias(true);
			TaskImageHelper.Arrow.drawArrow(canvas, w, h, paint4);

			break;
		case DRAW_RECTANGLE_ANSW:
			TaskImageHelper.Geometry.drawRectangle(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		case DRAW_RECTANGLE:
			TaskImageHelper.Geometry.drawRectangle(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		case DRAW_TWO_WAVE_LINES:
			TaskImageHelper.Geometry.drawTwoWaveLines(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		case DRAW_TWO_LINES:
			TaskImageHelper.Geometry.drawTwoDirectLines(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		case DRAW_FILL_RECTANGLE:
			TaskImageHelper.Geometry.drawFillRectangle(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		default:
			break;
		}

		super.onDraw(canvas);
	}

}
