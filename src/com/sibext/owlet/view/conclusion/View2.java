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

public class View2 extends BaseTaskImageView {
	private static final int DRAW_TWO_PERPENDICULAR_LINES = 0;
	private static final int DRAW_ARROW = 1;
	private static final int DRAW_TWO_WAVE_PERPENDICULAR_LINES = 2;
	private static final int DRAW_FOUR_PERPENDICULAR_LINES = 3;
	private static final int DRAW_ARROW1 = 4;
	private static final int DRAW_FOUR_WAVE_PERPENDICULAR_LINES_ANSW = 5;
	private static final int DRAW_FOUR_WAVE_PERPENDICULAR_LINES = 6;
	private static final int DRAW_TWO_VERTICAL_WAVE_AND_TWO_HORIZONT_DIRECT_PERPENDICULAR_LINES = 7;
	private static final int DRAW_ONE_HORIZONT_WAVE__AND_ONE_VERTICAL_DIRECT_PERPENDICULAR_LINES = 8;
	private static final int DRAW_ONE_VERTICAL_WAVE__AND_ONE_HORIZONT_DIRECT_PERPENDICULAR_LINES = 9;
	private Paint paint;

	public static int EXAMPLE_ID;
	public static int EXAMPLE_ANSWER_ID;
	public static int TEST_ID;
	public static int TEST_ANSWER_ID;
	public static ArrayList<Integer> variants;

	static{
		EXAMPLE_ID = DRAW_TWO_PERPENDICULAR_LINES;
		EXAMPLE_ANSWER_ID = DRAW_TWO_WAVE_PERPENDICULAR_LINES;
		TEST_ID = DRAW_FOUR_PERPENDICULAR_LINES;
		TEST_ANSWER_ID = DRAW_FOUR_WAVE_PERPENDICULAR_LINES_ANSW;
		variants = new ArrayList<Integer>();
		variants.add(DRAW_TWO_VERTICAL_WAVE_AND_TWO_HORIZONT_DIRECT_PERPENDICULAR_LINES);
		variants.add(DRAW_ONE_HORIZONT_WAVE__AND_ONE_VERTICAL_DIRECT_PERPENDICULAR_LINES);
		variants.add(DRAW_ONE_VERTICAL_WAVE__AND_ONE_HORIZONT_DIRECT_PERPENDICULAR_LINES);
	}
	
	public View2(Context context, Renderer renderer) {
		super(context, renderer);
		rendererIdLimit = 10;
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();

		switch (getRenderId()) {
		case DRAW_TWO_PERPENDICULAR_LINES:
			TaskImageHelper.Geometry.drawTwoPerpendLines(canvas,
					getColorFlag(), getColor(), w, h);			
			break;

		case DRAW_ARROW:
			TaskImageHelper.Arrow.drawArrow(canvas, w, h, paint);
			break;

		case DRAW_TWO_WAVE_PERPENDICULAR_LINES:
			TaskImageHelper.Geometry.drawTwoWavePerpendLines(canvas,
					getColorFlag(), getColor(), w, h);
			break;
		case DRAW_FOUR_PERPENDICULAR_LINES:
			TaskImageHelper.Geometry.drawFourPerpendLines(canvas,
					getColorFlag(), getColor(), w, h);
			break;
		case DRAW_ARROW1:
			TaskImageHelper.Arrow.drawArrow(canvas, w, h, paint);

			break;
		case DRAW_FOUR_WAVE_PERPENDICULAR_LINES_ANSW:
			TaskImageHelper.Geometry.drawFourWavePerpendLinesAns(canvas,
					getColorFlag(), getColor(), w, h);
			break;
		case DRAW_FOUR_WAVE_PERPENDICULAR_LINES:
			TaskImageHelper.Geometry.drawFourWavePerpendLines(canvas,
					getColorFlag(), getColor(), w, h);
			break;
		case DRAW_TWO_VERTICAL_WAVE_AND_TWO_HORIZONT_DIRECT_PERPENDICULAR_LINES:
			TaskImageHelper.Geometry.drawTwoWaveTwoDirectLines(canvas,
					getColorFlag(), getColor(), w, h);
			break;

		case DRAW_ONE_HORIZONT_WAVE__AND_ONE_VERTICAL_DIRECT_PERPENDICULAR_LINES:
			TaskImageHelper.Geometry.drawOneWaveOneDirectLines(canvas,
					getColorFlag(), getColor(), w, h);
			break;

		case DRAW_ONE_VERTICAL_WAVE__AND_ONE_HORIZONT_DIRECT_PERPENDICULAR_LINES:
			TaskImageHelper.Geometry.drawOneDirectOneWaveLines(canvas,
					getColorFlag(), getColor(), w, h);
			break;

		default:
			break;
		}

		super.onDraw(canvas);
	}

}
