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

public class View3 extends BaseTaskImageView {

	private static final int DRAW_FILL_TRIANGLE_IN_EMPTY_SQUARE = 0;
	private static final int DRAW_ARROW = 1;
	private static final int DRAW_FILL_SQUARE_IN_EMPTY_TRIANGLE = 2;
	private static final int DRAW_OVAL_IN_ROMB = 3;
	private static final int DRAW_ARROW1 = 4;
	private static final int DRAW_ROMB_IN_OVAL_ANSW = 5;
	private static final int DRAW_ROMB_IN_OVAL = 6;
	private static final int DRAW_FILL_OVAL_IN_EMPTY_ROMB = 7;
	private static final int DRAW_FILL_ROMB_IN_OVAL = 8;
	private static final int DRAW_TRIANGLE_IN_SQUARE = 9;
	
	public static int EXAMPLE_ID;
	public static int EXAMPLE_ANSWER_ID;
	public static int TEST_ID;
	public static int TEST_ANSWER_ID;
	public static ArrayList<Integer> variants;

	static{
		EXAMPLE_ID = DRAW_FILL_TRIANGLE_IN_EMPTY_SQUARE;
		EXAMPLE_ANSWER_ID = DRAW_FILL_SQUARE_IN_EMPTY_TRIANGLE;
		TEST_ID = DRAW_OVAL_IN_ROMB;
		TEST_ANSWER_ID = DRAW_ROMB_IN_OVAL_ANSW;
		variants = new ArrayList<Integer>();
		variants.add(DRAW_FILL_OVAL_IN_EMPTY_ROMB);
		variants.add(DRAW_FILL_ROMB_IN_OVAL);
		variants.add(DRAW_TRIANGLE_IN_SQUARE);
	}
	
	public View3(Context context, Renderer renderer) {
		super(context, renderer);
		rendererIdLimit = 6;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();

		switch (getRenderId()) {
		case DRAW_FILL_TRIANGLE_IN_EMPTY_SQUARE:
			TaskImageHelper.Geometry.drawFillTriangleInEmptySquare(canvas,
					getColorFlag(), getColor(), w, h);
			break;

		case DRAW_ARROW:

			Paint paint1 = new Paint();
			paint1.setAntiAlias(true);
			TaskImageHelper.Arrow.drawArrow(canvas, w, h, paint1);

			break;

		case DRAW_FILL_SQUARE_IN_EMPTY_TRIANGLE:
			TaskImageHelper.Geometry.drawFillSquareInEmptyTriangle(canvas,
					getColorFlag(), getColor(), w, h);
			break;

		case DRAW_OVAL_IN_ROMB:
			TaskImageHelper.Geometry.drawOvalInRomb(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		case DRAW_ARROW1:

			Paint paint4 = new Paint();
			TaskImageHelper.Arrow.drawArrow(canvas, w, h, paint4);

			break;

		case DRAW_ROMB_IN_OVAL_ANSW:
			TaskImageHelper.Geometry.drawRombInOval(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		case DRAW_ROMB_IN_OVAL:
			TaskImageHelper.Geometry.drawRombInOval(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		case DRAW_FILL_OVAL_IN_EMPTY_ROMB:
			TaskImageHelper.Geometry.drawFillOvalInEmptyRomb(canvas,
					getColorFlag(), getColor(), w, h);
			break;

		case DRAW_FILL_ROMB_IN_OVAL:
			TaskImageHelper.Geometry.drawFillRombInOval(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		case DRAW_TRIANGLE_IN_SQUARE:
			TaskImageHelper.Geometry.drawTriangleInSuare(canvas,
					getColorFlag(), getColor(), w, h);
			break;

		default:
			break;
		}

		super.onDraw(canvas);
	}

}
