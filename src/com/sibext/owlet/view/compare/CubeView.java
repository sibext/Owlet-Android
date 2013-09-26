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

package com.sibext.owlet.view.compare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.sibext.owlet.helper.BitmapHelper;
import com.sibext.owlet.view.BaseTaskImageView;
import com.sibext.owlet.view.TaskImageFactory;
import com.sibext.owlet.view.renderer.CombinatedRenderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.widget.FrameLayout;

public class CubeView extends BaseTaskImageView {
	private final int EXCESS_IDX = 3;
	private BaseTaskImageView currentTaskImageView;
	private int[] rendererId;
	private boolean excess = false;
	private Random rand;
	private int[] selected;
	private HashMap<Integer, Integer> colors;
	
	public CubeView(Context context, CombinatedRenderer renderer) {
		super(context, renderer);
		this.setDrawingCacheEnabled(true);
		this.rendererId = renderer.getMultId();
		this.excess = renderer.getExcessFlag();
		this.rendererIdLimit = renderer.getCombinatedLimit();
		this.colors = renderer.getMultColors();
		currentTaskImageView = TaskImageFactory.getImageView(renderer.getCombinatedType(), context, renderer);
		rand = new Random();
		//
		if (rendererId != null) {
			selected = new int[EXCESS_IDX];
			int[] buf = new int[EXCESS_IDX];
			for (int i = 0; i < (EXCESS_IDX); i++) {
				buf[i] = rendererId[i];
			}
			selected = shake(buf);

			if (excess) {
				int idx = rand.nextInt(selected.length);
				selected[idx] = rendererId[EXCESS_IDX];
			}
		}
	}
	
	private void doDraw(Canvas canvas, float src[], float dst[], int id, int w,
			int h) {
		Matrix mMatrix = new Matrix();
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.argb(150, 80, 164, 176));

		canvas.save();		
		mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
		canvas.concat(mMatrix);
		
		canvas.drawRect(0, 0, w, h, paint);
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);
		canvas.drawRect(0, 0, w, h, paint);
		currentTaskImageView.setColorFlag(getColorFlag());
		currentTaskImageView.setColor(colors.get(id));
		currentTaskImageView.setRenderId(id);
		currentTaskImageView.update();
		canvas.drawBitmap(
				BitmapHelper.getBitmapFromView(currentTaskImageView, w, h), 0, 0, paint);


		canvas.restore();
	}

	private int[] shake(int[] src) {
		int[] dst = new int[src.length];
		ArrayList<Integer> mas = new ArrayList<Integer>();
		for (int i = 0; i < src.length; i++) {
			mas.add(src[i]);
		}
		Random rand = new Random();
		for (int i = 0; i < src.length; i++) {
			int idx = rand.nextInt(mas.size());
			dst[i] = mas.get(idx).intValue();
			mas.remove(idx);
		}
		return dst;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		//
		int H = this.getHeight();
		int h = (int) (H * 30 / 47);
		int W = (int) (h * 5 / 3);
		int w = h;
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this
				.getLayoutParams();
		params.width = W;
		params.height = H;
		this.setLayoutParams(params);
		//
		float a = h / 5;
		float b = h / 6;
		float c = w / 2;
		float d = w - c;
		float e = a + b;

		canvas.save();		
		doDraw(canvas, new float[] { 0, 0, 0, h, w, 0, w, h }, new float[] { d,
				0, 0, e, 2 * w - c, a, w, e + a }, selected[0], w, h);
		canvas.restore();

		canvas.save();
		canvas.translate(0, e);
		doDraw(canvas, new float[] { 0, 0, 0, h, w, 0, w, h }, new float[] { 0,
				0, 0, h, w, a, w, h + a }, selected[1], w, h);
		canvas.restore();

		canvas.save();
		canvas.translate(w, a);
		doDraw(canvas, new float[] { 0, 0, 0, h, w, 0, w, h }, new float[] { 0,
				e, 0, h + e, w - c, 0, w - c, h }, selected[2], w, h);
		canvas.restore();

		super.onDraw(canvas);
	}

	@Override
	public void setupBackground(Context context, int resId) {
	}

}
