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

package com.sibext.owlet.view.transform;

import com.sibext.owlet.helper.BitmapHelper;
import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.renderer.ConclusionRenderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;

public class CountAndFillTramsformation extends Transformation {
	private final boolean isFill;
	private final int figureType;
	private final int number;
	private final boolean isFillDestination;
	private final int figureTypeDestination;
	private final int numberDestination;

	public static class Builder extends Transformation.Builder {
		private boolean isFill = true;
		private int figureType;
		private int number;
		private boolean isFillDestination = false;
		private int figureTypeDestination;
		private int numberDestination;

		public Builder(Context context) {
			super(context);
		}
		
		public Builder size(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}
		
		public Builder source(int figureType, int number, boolean isFill) {
			this.isFill = isFill;
			this.figureType = figureType;
			this.number = number;
			return this;
		}
		
		public Builder destination(int figureType, int number, boolean isFill) {
			this.isFillDestination = isFill;
			this.figureTypeDestination = figureType;
			this.numberDestination = number;
			return this;
		}
		
		public Builder color(boolean colorFlag, int color){
			this.colorFlag = colorFlag;
			this.color = color;
			return this;
		}
		
		@Override
		public Transformation build() {
			return new CountAndFillTramsformation(this);
		}
	}

	public CountAndFillTramsformation(Builder builder) {
		super(builder);
		this.isFill = builder.isFill;
		this.figureType = builder.figureType;
		this.number = builder.number;
		this.isFillDestination = builder.isFillDestination;
		this.figureTypeDestination = builder.figureTypeDestination;
		this.numberDestination = builder.numberDestination;
	}

	@Override
	public BaseTaskImageView[] transform() {
		//example
		Canvas canvas = new Canvas();
		Bitmap figure = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvas.setBitmap(figure);
		drawFigure(canvas, figureType, isFill, colorFlag, color, width, height);

		Bitmap[] bitmaps = new Bitmap[number];
		for(int i=0; i<bitmaps.length; i++){
			bitmaps[i] = figure;
		}
		Bitmap merge = BitmapHelper.mergeBitmaps(bitmaps);
		//answer
		Canvas canvas_dst = new Canvas();
		Bitmap figure_dst = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvas_dst.setBitmap(figure_dst);
		drawFigure(canvas_dst, figureTypeDestination, isFillDestination, colorFlag, color, width, height);
		
		Bitmap[] bitmaps_dst = new Bitmap[numberDestination];
		for(int i=0; i<bitmaps_dst.length; i++){
			bitmaps_dst[i] = figure_dst;
		}
		Bitmap merge_dst = BitmapHelper.mergeBitmaps(bitmaps_dst);
		//
		ConclusionRenderer conRenderer = new ConclusionRenderer();
		
		BaseTaskImageView[] res = new BaseTaskImageView[2];
		BaseTaskImageView sourse = new BaseTaskImageView(context,conRenderer);
		BaseTaskImageView dst = new BaseTaskImageView(context,conRenderer);
		sourse.setImageBitmap(merge);
		dst.setImageBitmap(merge_dst);
		res[0] = sourse;
		res[1] = dst;
		return res;
	}

}
