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

public class View5 extends BaseTaskImageView {

	private static final int DRAW_INVERTED_T = 0;
	private static final int DRAW_ARROW = 1;
	private static final int DRAW_THICK_INVERTED_T = 2;
	private static final int DRAW_T = 3;
	private static final int DRAW_ARROW1 = 4;
	private static final int DRAW_THICK_T_ANSW = 5;
	private static final int DRAW_THICK_T = 6;
	private static final int DRAW_TWO_T = 7;
	private static final int DRAW_T_WITH_THICK_LEG = 8;
	private static final int DRAW_T_WITH_THICK_HAT = 9;

	public  static int EXAMPLE_ID;
	public static int EXAMPLE_ANSWER_ID;
	public static int TEST_ID;
	public static int TEST_ANSWER_ID;
	public static ArrayList<Integer> variants;

	public View5(Context context, Renderer renderer) {
		super(context, renderer);
		rendererIdLimit = 10;
	}
	
	static{
		EXAMPLE_ID = DRAW_INVERTED_T;
		EXAMPLE_ANSWER_ID = DRAW_THICK_INVERTED_T;
		TEST_ID = DRAW_T;
		TEST_ANSWER_ID = DRAW_THICK_T;
		variants = new ArrayList<Integer>();
		variants.add(DRAW_TWO_T);
		variants.add(DRAW_T_WITH_THICK_LEG);
		variants.add(DRAW_T_WITH_THICK_HAT);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();

		switch (getRenderId()) {
		case DRAW_INVERTED_T:
			TaskImageHelper.TLikeFigure.drawInvertedT(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		case DRAW_ARROW:

			Paint paint1 = new Paint();
			paint1.setAntiAlias(true);
			TaskImageHelper.Arrow.drawArrow(canvas, w, h, paint1);

			break;

		case DRAW_THICK_INVERTED_T:
			TaskImageHelper.TLikeFigure.drawThickInvertedT(canvas,
					getColorFlag(), getColor(), w, h);
			break;
		case DRAW_T:
			TaskImageHelper.TLikeFigure.drawT(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		case DRAW_ARROW1:

			Paint paint4 = new Paint();
			paint4.setAntiAlias(true);
			TaskImageHelper.Arrow.drawArrow(canvas, w, h, paint4);

			break;
		case DRAW_THICK_T_ANSW:
			TaskImageHelper.TLikeFigure.drawThickTAns(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		case DRAW_THICK_T:
			TaskImageHelper.TLikeFigure.drawThickT(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		case DRAW_TWO_T:
			TaskImageHelper.TLikeFigure.drawTwoT(canvas, getColorFlag(),
					getColor(), w, h);
			break;

		case DRAW_T_WITH_THICK_LEG:
			TaskImageHelper.TLikeFigure.drawTwithThickLeg(canvas,
					getColorFlag(), getColor(), w, h);
			break;

		case DRAW_T_WITH_THICK_HAT:
			TaskImageHelper.TLikeFigure.drawTwithThickHat(canvas,
					getColorFlag(), getColor(), w, h);
			break;

		default:
			break;
		}

		super.onDraw(canvas);
	}

}
