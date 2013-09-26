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

public class View1 extends BaseTaskImageView{
	private static final int HALF_FILL_OVAL = 0;
	private static final int ARROW = 1;
	private static final int HALF_EMPTY_OVAL = 2;
	private static final int HALF_FILL_SQUARE = 3;
	private static final int ARROW1 = 4;
	private static final int HALF_EMPTY_SQUARE_ANSW = 5;
	private static final int HALF_EMPTY_SQUARE = 6;
	private static final int EMPTY_OVAL = 7;
	private static final int EMPTY_SQUARE = 8;
	private static final int FILL_SQUARE = 9;
	
	public static int EXAMPLE_ID;
	public static int EXAMPLE_ANSWER_ID;
	public static int TEST_ID;
	public static int TEST_ANSWER_ID;
	public static ArrayList<Integer> variants;

	static{
		EXAMPLE_ID = HALF_FILL_OVAL;
		EXAMPLE_ANSWER_ID = HALF_EMPTY_OVAL;
		TEST_ID = HALF_FILL_SQUARE;
		TEST_ANSWER_ID = HALF_EMPTY_SQUARE;
		variants = new ArrayList<Integer>();
		variants.add(EMPTY_OVAL);
		variants.add(EMPTY_SQUARE);
		variants.add(FILL_SQUARE);
	}
	
	public View1(Context context, Renderer renderer) {
		super(context, renderer);
		rendererIdLimit = 6; 
	}


	@Override
	protected void onDraw(Canvas canvas) {
		int w = this.getWidth();
		int h = this.getHeight();		

		switch (getRenderId()) {
		
		case HALF_FILL_OVAL:
			TaskImageHelper.Geometry.drawHalfFillOval(canvas, getColorFlag(),
					getColor(), w, h);
			break;
		
		case ARROW:
			
			Paint paint1 = new Paint();
			paint1.setAntiAlias(true);
			TaskImageHelper.Arrow.drawArrow(canvas, w, h, paint1);	
			
			break;
			
		case HALF_EMPTY_OVAL:
			TaskImageHelper.Geometry.drawHalfEmptyOval(canvas, getColorFlag(),
					getColor(), w, h);
			break;
			
		case HALF_FILL_SQUARE:
			TaskImageHelper.Geometry.drawHalfFillSquare(canvas, getColorFlag(),
					getColor(), w, h);
			break;
			
		case ARROW1:
			
			Paint paint4 = new Paint();
			paint4.setAntiAlias(true);
			TaskImageHelper.Arrow.drawArrow(canvas, w, h, paint4);
			
			break;
			
		case HALF_EMPTY_SQUARE_ANSW:
			TaskImageHelper.Geometry.drawHalfEmptySquareAns(canvas, getColorFlag(), getColor(), w, h);
			break;
			
		case HALF_EMPTY_SQUARE:
			TaskImageHelper.Geometry.drawHalfEmptySquareAns(canvas, getColorFlag(), getColor(), w, h);
			break;
			
		case EMPTY_OVAL:
			TaskImageHelper.Geometry.drawCircle(canvas, getColorFlag(), getColor(), w, h);
			break;
		
		case EMPTY_SQUARE:
			TaskImageHelper.Geometry.drawSquare(canvas, getColorFlag(), getColor(), w, h);
			break;
		
		case FILL_SQUARE:
			TaskImageHelper.Geometry.drawSquareFill(canvas, getColorFlag(), getColor(), w, h);
			break;
	
		default:
			break;
		}
		
		super.onDraw(canvas);
	}


}
