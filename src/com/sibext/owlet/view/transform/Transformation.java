package com.sibext.owlet.view.transform;

import com.sibext.owlet.helper.TaskImageHelper;
import com.sibext.owlet.view.BaseTaskImageView;
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

import android.content.Context;
import android.graphics.Canvas;


public class Transformation {
	protected final int width;
	protected final int height;
	protected final boolean colorFlag;
	protected final int color;

	public static class FigureTypes {
		public static final int CIRCEL = 0;
		public static final int TRIANGLE = 1;
		public static final int SQUARE = 2;
		
		public static final int FIGURE_COUNT = 3;
	}
	
	protected final Context context;
	
	public abstract static class Builder {
		protected final Context context;
		protected int width = 10;
		protected int height = 10;
		protected boolean colorFlag;
		protected int color;

		public Builder(Context context) {
			this.context = context;
		}

		public Transformation build() {
			return new Transformation(this);
		}
		
	}
	
	Transformation(Builder builder) {
		this.context = builder.context;
		this.width = builder.width;
		this.height = builder.height;
		this.colorFlag = builder.colorFlag;
		this.color = builder.color;
	}

	/**
	 * The psevdo-abstract method
	 * @return
	 */
	public BaseTaskImageView[] transform(){ return null;}

	
	protected static void drawFigure(Canvas canvas, int type, boolean fill,
			boolean colorFlag, int color, int w, int h) {
		switch (type) {
		case FigureTypes.CIRCEL:
			if(fill){
				TaskImageHelper.Geometry.drawCircleFill(canvas, colorFlag, color, w, h);
			} else {
				TaskImageHelper.Geometry.drawCircle(canvas, colorFlag, color, w, h);				
			}
			break;
		case FigureTypes.TRIANGLE:
			if(fill){
				TaskImageHelper.Geometry.drawTriangleFill(canvas, colorFlag, color, w, h);
			} else {
				TaskImageHelper.Geometry.drawTriangle(canvas, colorFlag, color, w, h);
			}
			break;
		case FigureTypes.SQUARE:
			if(fill){
				TaskImageHelper.Geometry.drawSquareFill(canvas, colorFlag, color, w, h);
			} else {
				TaskImageHelper.Geometry.drawSquare(canvas, colorFlag, color, w, h);				
			}
			break;
		}
	}
}
