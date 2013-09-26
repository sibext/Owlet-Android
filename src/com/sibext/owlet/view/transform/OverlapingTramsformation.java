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

public class OverlapingTramsformation extends Transformation {
	private final boolean isFillUp;
	private final boolean isFillDn;
	private final int figureTypeUp;
	private final int figureTypeDn;
	
	public static class Builder extends Transformation.Builder{
		private boolean isFillUp = true;
		private boolean isFillDn = true;
		private int figureTypeUp;
		private int figureTypeDn;
		
		public Builder(Context context){
			super(context);
		}
		
		public Builder size(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}
		
		public Builder up(boolean isFill,int figureType){
			this.isFillUp = isFill;
			this.figureTypeUp = figureType;
			return this;
		}
		
		public Builder dn(boolean isFill,int figureType){
			this.isFillDn = isFill;
			this.figureTypeDn = figureType;
			return this;			
		}
		public Builder color(boolean colorFlag, int color){
			this.colorFlag = colorFlag;
			this.color = color;
			return this;
		}
		
		@Override
		public Transformation build() {
			return new OverlapingTramsformation(this);
		}

	}

	public OverlapingTramsformation(Builder builder) {
		super(builder);
		this.isFillUp = builder.isFillUp;
		this.isFillDn = builder.isFillDn;
		this.figureTypeUp = builder.figureTypeUp;
		this.figureTypeDn = builder.figureTypeDn;
	}

	@Override
	public BaseTaskImageView[] transform() {
		//example
		Canvas canvasUp = new Canvas();
		Bitmap up = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvasUp.setBitmap(up);
		Canvas canvasDn = new Canvas();
		Bitmap dn = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvasDn.setBitmap(dn);
		drawFigure(canvasUp, figureTypeUp, isFillUp, colorFlag, color, width, height);
		drawFigure(canvasDn, figureTypeDn, isFillDn, colorFlag, color, width, height);
		
		Bitmap sourse = BitmapHelper.mergeTwoBitmapWithOverlaping(up, dn);
		
		//answer
		Canvas canvasUpDst = new Canvas();
		Bitmap upDst = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvasUpDst.setBitmap(upDst);
		Canvas canvasDnDst = new Canvas();
		Bitmap dnDst = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		canvasDnDst.setBitmap(dnDst);
		drawFigure(canvasUpDst, figureTypeUp, !isFillUp, colorFlag, color, width, height);
		drawFigure(canvasDnDst, figureTypeDn, !isFillDn, colorFlag, color, width, height);
		
		Bitmap dst = BitmapHelper.mergeTwoBitmapWithOverlaping(upDst, dnDst);
		
		ConclusionRenderer conRenderer = new ConclusionRenderer();
		
		BaseTaskImageView[] res = new BaseTaskImageView[2];
		BaseTaskImageView src = new BaseTaskImageView(context,conRenderer);
		BaseTaskImageView dest = new BaseTaskImageView(context,conRenderer);
		src.setImageBitmap(sourse);
		dest.setImageBitmap(dst);
		res[0] = src;
		res[1] = dest;
		return res;		
	}

}
