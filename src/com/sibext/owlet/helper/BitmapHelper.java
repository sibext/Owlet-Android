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

package com.sibext.owlet.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class BitmapHelper {
	
	public static Bitmap mergeTwoBitmapWithOverlaping(Bitmap up,Bitmap down){
		Bitmap cs = null;
		int width, height = 0;
		final Bitmap first = Bitmap
				.createScaledBitmap(up, up.getWidth() / 2,
						up.getHeight() / 2, true);
		final Bitmap second = Bitmap
				.createScaledBitmap(down, (int)(down.getWidth() / 1.5),
						(int)(down.getHeight() / 1.5), true);
		width = up.getWidth();
		height = up.getHeight();
		cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas twiseImage = new Canvas(cs);

		twiseImage.drawBitmap(first, width - (int)(first.getWidth()*2), height - (int)(first.getHeight()), null);
		twiseImage.drawBitmap(second, width - second.getWidth(),0f, null);
		
		return cs;
	}
	/**
	 * The stupid merge for android bitmaps
	 * How use:
	 * 	Drawable dr = getResources().getDrawable(R.drawable.cherry);
		Bitmap bitmap = BitmapHelper.mergeBitmaps(
			((BitmapDrawable)dr).getBitmap(), 
			((BitmapDrawable)dr).getBitmap(), 
			((BitmapDrawable)dr).getBitmap());
		Drawable result = new BitmapDrawable(getResources(), bitmap);
	 *
	 * @param bitmaps
	 *            the list of bitmaps
	 * @return merged bitmap
	 */
	public static Bitmap mergeBitmaps(Bitmap... bitmaps) {
		Bitmap cs = null;

		int width, height = 0;
		int size = bitmaps.length;
		if(size==1){
			return bitmaps[0];
		} else if (size == 2) {
			final Bitmap first = Bitmap
					.createScaledBitmap(bitmaps[0], bitmaps[0].getWidth() / 2,
							bitmaps[0].getHeight() / 2, true);
			final Bitmap second = Bitmap
					.createScaledBitmap(bitmaps[1], bitmaps[1].getWidth() / 2,
							bitmaps[1].getHeight() / 2, true);
			width = bitmaps[0].getWidth();
			height = bitmaps[0].getHeight();
			cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

			Canvas twiseImage = new Canvas(cs);

			twiseImage.drawBitmap(first, width - first.getWidth(), 0f, null);
			twiseImage.drawBitmap(second, 0f, height - second.getHeight(), null);
		} else if (size == 3) {
			final Bitmap first = Bitmap
					.createScaledBitmap(bitmaps[0], bitmaps[0].getWidth() / 2,
							bitmaps[0].getHeight() / 2, true);
			final Bitmap second = Bitmap
					.createScaledBitmap(bitmaps[1], bitmaps[1].getWidth() / 2,
							bitmaps[1].getHeight() / 2, true);
			final Bitmap third = Bitmap
					.createScaledBitmap(bitmaps[2], bitmaps[2].getWidth() / 2,
							bitmaps[2].getHeight() / 2, true);
			width = bitmaps[0].getWidth();
			height = bitmaps[0].getHeight();
			cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			
			Canvas twiseImage = new Canvas(cs);
			
			twiseImage.drawBitmap(first, width - first.getWidth(), height - second.getHeight(), null);
			twiseImage.drawBitmap(second, 0f, height - second.getHeight(), null);
			twiseImage.drawBitmap(third, (width - second.getWidth()) / 2, 0f, null);		
	} else if (size == 4) {
		final Bitmap first = Bitmap
				.createScaledBitmap(bitmaps[0], bitmaps[0].getWidth() / 2,
						bitmaps[0].getHeight() / 2, true);
		final Bitmap second = Bitmap
				.createScaledBitmap(bitmaps[1], bitmaps[1].getWidth() / 2,
						bitmaps[1].getHeight() / 2, true);
		final Bitmap third = Bitmap
				.createScaledBitmap(bitmaps[2], bitmaps[2].getWidth() / 2,
						bitmaps[2].getHeight() / 2, true);
		final Bitmap fourth = Bitmap
				.createScaledBitmap(bitmaps[2], bitmaps[2].getWidth() / 2,
						bitmaps[2].getHeight() / 2, true);
		width = bitmaps[0].getWidth();
		height = bitmaps[0].getHeight();
		cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		
		Canvas twiseImage = new Canvas(cs);
		
		twiseImage.drawBitmap(first, width - first.getWidth(), height - second.getHeight(), null);
		twiseImage.drawBitmap(second, 0f, height - second.getHeight(), null);
		twiseImage.drawBitmap(third, 0f, 0f, null);
		twiseImage.drawBitmap(fourth, (width - second.getWidth()), 0f, null);
	}
		return cs;
	}

	public static Bitmap getBitmapFromView(View v, int w, int h) {
		Bitmap b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		v.layout(0, 0, w, h);
		v.draw(c);
		return b;
	}

}
